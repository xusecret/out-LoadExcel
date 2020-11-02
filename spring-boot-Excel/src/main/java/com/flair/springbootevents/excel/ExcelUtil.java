package com.flair.springbootevents.excel;

import com.flair.springbootevents.Entity.Person;
import com.flair.springbootevents.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExcelUtil {
    private static final Logger log = LoggerFactory.getLogger( ExcelUtil.class );
    private static final Integer DEFAULT_MAX_GROUP_LINE = 500;
    public static String varifyFileName(String fileName) {
        if( StringUtil.isEmpty( fileName ) || ( !WDWUtil.isExcel2003( fileName )
                && !WDWUtil.isExcel2007( fileName ) ) ){
            return "文件名不是excel格式.";
        }
        return null;
    }

    //解析导入的excel文件  可封装一个实体类用于接收Excel导入
    public static String parseDeviceGroupInfo(List<Person> list , MultipartFile file, String fileName) {
        InputStream in = null;
        Boolean isExcel2003=true;
        if( WDWUtil.isExcel2007( fileName ) ){
            isExcel2003 = false;
        }
        try {
            in = file.getInputStream();
            Workbook workbook = null;
            if( isExcel2003 ){
                workbook = new HSSFWorkbook( in );
            }else {
                workbook = new XSSFWorkbook( in );
            }
            Sheet sheet = workbook.getSheetAt( 0 );
            Integer max = 200;
            max = ( max == null ? DEFAULT_MAX_GROUP_LINE : max );
            //获得excel行数
            int groupLines = sheet.getPhysicalNumberOfRows();
            if( max < groupLines - 1 ){
                return "excel行数数必须小于" + max;
            }
            if( groupLines <= 1 ){
                return "excel 无数据";
            }

            Row title = sheet.getRow( 0 );
            //获得excel表格的列数
            int cellNum = title.getPhysicalNumberOfCells();
            if(cellNum !=2){
                return "excel列数错误";
            }
            for( int r = 1; r < groupLines; r++ ){
                Row dataRow = sheet.getRow( r );
                if( dataRow == null )
                    continue;
                Person bo = new Person();
                //获取第二行 第一列数据
                Cell rdbsnCell = dataRow.getCell(0);
                rdbsnCell.setCellType( CellType.STRING );
                //获取第二行 第二列数据
                Cell itemPnCell = dataRow.getCell(1);
                itemPnCell.setCellType( CellType.STRING );
                System.out.println(rdbsnCell.getStringCellValue());
                bo.setName( rdbsnCell == null ? null : rdbsnCell.getStringCellValue() );
                bo.setAge( itemPnCell == null ? null : Integer.parseInt(itemPnCell.getStringCellValue()) );
                list.add( bo );
            }
            return null;
        }catch ( IOException e ){
            log.error("分组excel表格解析错误",e);
            return "excel 格式错误";
        }finally {
            if( in != null ){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static String parsFactoryInfo(List<FactoryEntity> entityList, MultipartFile file, String fileName) {
//        boolean isExcel2003 = true;
//        if( WDWUtil.isExcel2007( fileName ) ){
//            isExcel2003 = false;
//        }
//        InputStream in = null;
//        try {
//            in = file.getInputStream();
//            Workbook workbook = null;
//            if( isExcel2003 ){
//                workbook = new HSSFWorkbook( in );
//            }else {
//                workbook = new XSSFWorkbook( in );
//            }
//            Sheet sheet = workbook.getSheetAt( 0 );
//            int groupLines = sheet.getPhysicalNumberOfRows();
//            if( groupLines <= 1 ){
//                return "excel 无数据";
//            }
//            Row title = sheet.getRow( 0 );
//            int cellNum = title.getPhysicalNumberOfCells();
//            if( cellNum != Const.FACTORY_EXCEL_CELL_NUM){
//                return "excel 格式错误";
//            }
//            for( int r = 1; r < groupLines; r++ ){
//                Row dataRow = sheet.getRow( r );
//                if( dataRow == null )
//                    continue;
//                FactoryEntity entity = new FactoryEntity();
//                Cell codeCell = dataRow.getCell(0);
//                codeCell.setCellType( CellType.STRING );
//                Cell nameCell = dataRow.getCell(1);
//                nameCell.setCellType( CellType.STRING );
//                entity.setId( codeCell.getStringCellValue() );
//                entity.setFactoryName( nameCell.getStringCellValue() );
//                entityList.add( entity );
//            }
//            return null;
//        }catch ( IOException e ){
//            log.error("分组excel表格解析错误",e);
//            return "excel 格式错误";
//        }finally {
//            if( in != null ){
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}

    class WDWUtil {
        public static boolean isExcel2003(String filePath) {
            return filePath.matches("^.+\\.(?i)(xls)$");
        }
        public static boolean isExcel2007(String filePath) {
            return filePath.matches("^.+\\.(?i)(xlsx)$");
        }
    }
