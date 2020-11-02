package com.flair.springbootevents.main;

import com.flair.springbootevents.Entity.Person;
import com.flair.springbootevents.Entity.Teacher;

import java.lang.reflect.Field;

public class DoMain5 {
    public static void main(String[] args) {
        Person person=new Person("王二",19,"北京","湖北市");
        Teacher teacher = new Teacher();
        Field[] declaredFields = person.getClass().getDeclaredFields();
        try {
            for (Field field:declaredFields) {
                String name = field.getName();
                field.setAccessible(true);
                Field declaredField = teacher.getClass().getDeclaredField(name);
                declaredField.setAccessible(true);
                Object o = field.get(person);
                if(name.equals("address")){
                    declaredField.set(teacher,o.toString().getBytes());
                }else {
                    declaredField.set(teacher,o);
                }

            }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        System.out.println(teacher);
    }



}
