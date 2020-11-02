package com.flair.springbootevents.util;

import com.flair.springbootevents.excel.ExcelExportUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class MyExcelUtil {
    //填充返回内容
    public static  <t> void outFillResponse(String fileName, HttpServletResponse response, HttpServletRequest request, List<t> ds, Class c
    ) {
        //创建ExcelExportUtil对象时候就会进行反射
        ExcelExportUtil<t> util = new ExcelExportUtil<>( c);
        for(t st:ds){
            util.fillObject(st);
        }
        OutputStream out = null;
        try {
            out =  response.getOutputStream();
            response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
            String agent = request.getHeader("USER-AGENT");
            if(agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
                fileName =  new String(fileName.getBytes("UTF-8"),"iso-8859-1");
            }else{
                fileName =  java.net.URLEncoder.encode(fileName, "UTF-8");
            }
            response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xls");
            response.setContentType("application/vnd.ms-excel");//定义输出类型
            util.write( out );
        }catch (Exception e){
            return;
        }finally {
            if( out != null ){
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
