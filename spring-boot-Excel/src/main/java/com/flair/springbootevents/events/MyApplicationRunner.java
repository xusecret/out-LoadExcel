package com.flair.springbootevents.events;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component//比commandLineRunner先执行
public class MyApplicationRunner implements ApplicationRunner{
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(args);
        System.out.println("ApplicationArguments...run...执行了");
    }
}
