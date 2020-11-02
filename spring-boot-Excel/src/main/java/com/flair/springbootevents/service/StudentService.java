package com.flair.springbootevents.service;

import com.flair.springbootevents.Entity.Person;
import com.flair.springbootevents.Entity.Student;
import com.flair.springbootevents.excel.ExcelExportUtil;
import com.flair.springbootevents.util.MyExcelUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    public void export(HttpServletRequest request, HttpServletResponse response){
        List<Student> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Student student = new Student("张三丰",1+i,"成都");
            list.add(student);
        }
       MyExcelUtil.outFillResponse("Excel导出",response,request,list,Student.class);
    }



}
