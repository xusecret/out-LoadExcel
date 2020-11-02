package com.flair.springbootevents.main;

import com.flair.springbootevents.Entity.Person;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DoMain2 {
    public static void main(String[] args) {
         Person person = new Person("张三",15,"湖南","东北");

//        Method[] methods = person.getClass().getMethods();
//        for(Method method:methods){
//            System.out.println(method);
//        }
        List<Class> list=new ArrayList<>();
        try {
            String str="java.lang.String";
            Class<?> abc = Class.forName(str);
            list.add(abc);
            Class[] classes = list.toArray(new Class[3]);
            for(Class c:classes){
                System.out.println(c);
            }
            Method getName = person.getClass().getMethod("setName",classes );
            System.out.println(getName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){

        }
    }



}
