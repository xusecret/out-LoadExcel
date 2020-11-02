package com.flair.springbootevents.Entity;

import java.util.Arrays;

public class Teacher {
    private String name;
    private Integer age;
    private byte[] address;
    private String home;
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

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Teacher(String name, Integer age, byte[] address, String home) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.home = home;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + Arrays.toString(address) +
                ", home='" + home + '\'' +
                '}';
    }
}
