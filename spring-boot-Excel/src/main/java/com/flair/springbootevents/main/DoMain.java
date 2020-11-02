package com.flair.springbootevents.main;

import com.flair.springbootevents.Entity.Person;
import com.flair.springbootevents.Entity.Student;

import java.lang.reflect.Field;

public class DoMain {
    public static void main(String[] args) throws Exception{
        //通过反射赋值  将person赋值到student上
        Person person=new Person("张三",15,"湖南","东北");
        Student student = new Student();
        Field[] declaredFields = student.getClass().getDeclaredFields();
        int length = declaredFields.length;
        System.out.println(length);
        for(Field field:declaredFields){
            field.setAccessible(true);
            String name = field.getName();
            Field declaredField = person.getClass().getDeclaredField(name);
            System.out.println(declaredField);
            declaredField.setAccessible(true);
            Object o = declaredField.get(person);
            System.out.println(o);
            field.set(student,o);
            System.out.println(person);
        }
        System.out.println(student);

    }
}
