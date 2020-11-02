package com.flair.springbootevents.excel;


import com.flair.springbootevents.annotation.ExcelExportConfig;
import com.flair.springbootevents.util.StringUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelExportUtil<T>{
    private static Map<Class,List<ExcelConfigItem>> cache = new ConcurrentHashMap<>();
    private static Map<Class,Transformer> cacheTransformer = new ConcurrentHashMap<>();
    private static Logger log = LoggerFactory.getLogger(ExcelExportUtil.class);
    private static class ExcelConfigItem{
        String name;
        Field field;
        Boolean ignoreName;
        ExcelConfigItem subField;
        String format;
        int width;
        Class<? extends Transformer> tranClass;
        boolean isDate = false;
        public String getName(){
            StringBuilder sb = new StringBuilder();
            ExcelConfigItem sub = this;
            while (true){
                if( !sub.ignoreName ){
                    sb.append(sub.name);
                }
                if( sub.subField != null ){
                    sub = sub.subField;
                }else {
                    break;
                }
            }
            return sb.toString();
        }
        //获取宽度
        public int getWidth(){
            int w = width;
            ExcelConfigItem sub = this;
            while (true){
                w = sub.width;
                if( sub.subField != null ){
                    sub = sub.subField;
                }else {
                    break;
                }
            }
            return w;
        }

        public Object getValue( Object target ) throws IllegalAccessException, InstantiationException {
            field.setAccessible(true);
            Object val = field.get( target );
            Transformer transformer = cacheTransformer.get( tranClass );
            if( transformer == null ){
                transformer = tranClass.newInstance();
                Transformer nullableTrans = cacheTransformer.putIfAbsent(tranClass,transformer);
                if( nullableTrans != null ){
                    transformer = nullableTrans;
                }
            }
            val = transformer.transform(field.getName(),val);
            if( isDate ){
                Date date = (Date)val;
                if( date != null ){
                    SimpleDateFormat sdf = new SimpleDateFormat( format );
                    return sdf.format( date );
                }else {
                    return "";
                }
            }
            if( subField != null ){
                if( val != null ){
                    return subField.getValue( val );
                }else {
                    return "";
                }
            }else {
                return val == null ? "" : val;
            }
        }
        public ExcelConfigItem copy(){
            ExcelConfigItem i = new ExcelConfigItem();
            i.name = name;
            i.field = field;
            i.ignoreName = ignoreName;
            i.format = format;
            i.isDate = isDate;
            i.tranClass = tranClass;
            i.width = width;
            if( subField != null ){
                i.subField = subField.copy();
            }
            return i;
        }

        public void addItem(ExcelConfigItem item) {
            ExcelConfigItem sub = this;
            while (true){
                if( sub.subField == null ){
                    sub.subField = item;
                    break;
                }else {
                    sub = sub.subField;
                }
            }
        }
    }
    private Class<T> aimClass;
    private List<ExcelConfigItem> fields = new ArrayList<>();
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private XSSFWorkbook book;
    private int rowIndex = 0;
    public ExcelExportUtil(Class<T> aimClass) {
        this.aimClass = aimClass;
        open();
    }

    public ExcelExportUtil(Class<T> aimClass,String dateFormat) {
        this.aimClass = aimClass;
        if( StringUtil.isEmpty( dateFormat ) ){
            throw new RuntimeException();
        }
        this.dateFormat = dateFormat;
        open();
    }

    private void open(){
        book = new XSSFWorkbook();
        List<ExcelConfigItem> list = cache.get( aimClass );
        if( list != null ){
            fields = list;
        }else {
            fields = new ArrayList<>();
            fillData( null,aimClass );
            cache.putIfAbsent( aimClass,fields );
        }
        Sheet sheet = book.createSheet();
        Row row = sheet.createRow(rowIndex);
        for( int i = 0,len = fields.size(); i < len; i++ ){
            Cell cell = row.createCell( i );
            cell.setCellValue( fields.get( i ).getName() );
        }
        rowIndex++;
    }

    private void fillData(ExcelConfigItem father, Class<T> aim) {
        Field[] fieldList = aim.getDeclaredFields();
        for( Field f : fieldList ){
            ExcelExportConfig config =f.getAnnotation(ExcelExportConfig.class);
            if( config != null ){
                ExcelConfigItem item = new ExcelConfigItem();
                Class<? extends Transformer> transClass = config.transfomer();
                item.field = f;
                item.tranClass = transClass;
                item.width = config.width();
                Class fieldType = f.getType();
                boolean ignoreName = config.ignoreName();
                String name = config.name();
                if( Date.class.isAssignableFrom( fieldType ) ){
                    if( StringUtil.isEmpty( name ) ){
                        name = f.getName();
                    }
                    String format = config.format();
                    item.isDate = true;
                    item.format = format;
                    item.ignoreName = false;
                    item.name = name;
                    addTofields( father,item );
                }else if( fieldType.isPrimitive() ||
                        Number.class.isAssignableFrom( fieldType ) ||
                        Boolean.class.isAssignableFrom( fieldType ) ||
                        String.class.isAssignableFrom( fieldType ) ){
                    if( StringUtil.isEmpty( name ) ){
                        name = f.getName();
                    }
                    item.isDate = false;
                    item.ignoreName = false;
                    item.name = name;
                    addTofields( father,item );
                }else{
                    item.ignoreName = ignoreName;
                    if( !ignoreName ){
                        if( StringUtil.isEmpty( name ) ){
                            name = f.getName();
                        }
                        item.name = name;
                    }
                    item.isDate = false;
                    if( father == null ){
                        fillData( item,fieldType );
                    }else {
                        father.addItem( item );
                        fillData( father,fieldType );
                    }

                }
            }
        }

    }

    private void addTofields(ExcelConfigItem father, ExcelConfigItem item) {
        if( father == null ){
            fields.add( item );
        }else {
            ExcelConfigItem eci = father.copy();
            eci.addItem( item );
            fields.add( eci );
        }
    }

    public void write(OutputStream out) throws IOException {
        Sheet sheet = book.getSheetAt(0);
        for( int i = 0; i < fields.size(); i++ ){
            int w = fields.get(i).getWidth();
            if(w <= 0){
                //调整列的宽度 自适应
                sheet.autoSizeColumn( i );
            }else {
                sheet.setColumnWidth( i,w );
            }
        }
        book.write( out );
        book.close();
    }

    //填充对象
    public void fillObject( T t ) {
        Sheet sheet = book.getSheetAt(0);
        Row row = sheet.createRow(rowIndex);
        for( int i = 0,len = fields.size(); i < len; i++ ){
            Cell cell = row.createCell( i );
            String val = "";
            try {
                Object res = fields.get( i ).getValue( t );
                if( res != null ){
                    val = res.toString();
                }
            }catch (IllegalAccessException e){
                log.error("excel 导出，反射字段获取失败");
            }catch (InstantiationException ie){
                log.error("excel 导出，转换器实例化失败");
            }
            cell.setCellValue( val );
        }
        rowIndex++;
    }
}
