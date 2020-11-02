package com.flair.springbootevents.main;

import com.flair.springbootevents.Entity.Student;
import com.flair.springbootevents.annotation.SortField;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Domain3 {
        public static void main(String[] args) {
            Set<String> students=new HashSet<>();
            Class<Student> studentClass = Student.class;
            Field[] declaredFields = studentClass.getDeclaredFields();
            for(Field field:declaredFields){
                SortField annotation = field.getAnnotation(SortField.class);
                if(annotation!=null){
                    String name = field.getName();
                    System.out.println(name);
                    Class type = field.getType();
                    if(type.isArray() ){
                        continue;
                    }
                    if(type.isPrimitive()){
                        students.add(""+name);
                        continue;
                    }
                    if( Number.class.isAssignableFrom( type ) ||
                            Date.class.isAssignableFrom( type ) ||
                            String.class.isAssignableFrom( type )){
                        students.add( "" + name );
                        continue;
                    }
                }
            }
            for(String str:students){
                System.out.println(str);
            }


        }
}
