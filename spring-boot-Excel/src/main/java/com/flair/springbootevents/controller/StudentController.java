package com.flair.springbootevents.controller;

import com.flair.springbootevents.Entity.Student;
import com.flair.springbootevents.annotation.MyAnnotation;
import com.flair.springbootevents.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    /**
     * 导出
     * @param response
     * @param request
     */
    @MyAnnotation
    @RequestMapping("st/export")
    public void export(HttpServletResponse response, HttpServletRequest request){
        studentService.export(request,response);
    }

}
