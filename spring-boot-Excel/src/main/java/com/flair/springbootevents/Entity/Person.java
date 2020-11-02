package com.flair.springbootevents.Entity;


import com.flair.springbootevents.annotation.ExcelExportConfig;
import com.flair.springbootevents.annotation.MyAnnotation;

@MyAnnotation(value = "321")
public class Person {

    @ExcelExportConfig(name="姓名")
    private String name;
    @ExcelExportConfig(name = "年龄")
    private Integer age;
    private String address;
    @ExcelExportConfig(name = "地址")
    private String home;
   // private Boolean isBoos;

    public Person() {
    }

    public Person(String name, Integer age, String address, String home) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.home = home;
        //this.isBoos = isBoos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
