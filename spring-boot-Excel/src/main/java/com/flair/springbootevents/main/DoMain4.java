package com.flair.springbootevents.main;

import com.alibaba.fastjson.JSONObject;
import com.flair.springbootevents.Entity.Person;

public class DoMain4{
    public static void main(String[] args) {
        //判断是否为 默认分组  5
//        Boolean isDefault=null;
//        boolean b = (!(isDefault != null && isDefault));
//        System.out.println(b);

        Person pre=new Person();

        pre.setAge(23);
        pre.setAddress("北京市西城区");
        pre.setName("JSON");
        System.out.println(pre);
        //1、使用JSONObject
        //转成json对象
        Object o = JSONObject.toJSON(pre);
        String s1 = o.toString();
        System.out.println("s1"+s1);

        System.out.println(o);
        //JSONObject.toJSONString()
        //转成json格式的字符串
        String s = JSONObject.toJSONString(pre);
        System.out.println(s);

        Person parse = JSONObject.parseObject(s,Person.class);
        System.out.println(parse);

    }
}
