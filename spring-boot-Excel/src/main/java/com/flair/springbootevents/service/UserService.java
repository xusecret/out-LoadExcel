package com.flair.springbootevents.service;

import com.flair.springbootevents.Entity.Person;
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
public class UserService {
    public void export(HttpServletRequest request, HttpServletResponse response){
        List<Person>list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Person person=new Person("张三12333333333333333333333333333333333333333333333",15+i,"湖南","东北");
            list.add(person);
        }
        MyExcelUtil.outFillResponse("Excel导出",response,request,list,Person.class);
    }


}
