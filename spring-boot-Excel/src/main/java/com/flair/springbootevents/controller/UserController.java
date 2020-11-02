package com.flair.springbootevents.controller;

import com.flair.springbootevents.Entity.Person;
import com.flair.springbootevents.annotation.MyAnnotation;
import com.flair.springbootevents.excel.ExcelUtil;
import com.flair.springbootevents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 导出
     * @param response
     * @param request
     */
    @MyAnnotation
    @RequestMapping("export")
    public void export(HttpServletResponse response, HttpServletRequest request){
        userService.export(request,response);
    }

    @RequestMapping("upload")
    public String uploadExcel(MultipartFile file){
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String result = ExcelUtil.varifyFileName( fileName );
        if(result!=null){
            return  result;
        }
        List<Person>list=new ArrayList<>();
        //解析导入的excel表格
        String s = ExcelUtil.parseDeviceGroupInfo(list, file, fileName);
        for(Person person : list){
            System.out.println(person);
        }
        return s;
    }
}
