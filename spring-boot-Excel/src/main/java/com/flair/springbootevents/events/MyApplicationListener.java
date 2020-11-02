package com.flair.springbootevents.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;

public class MyApplicationListener extends ApplicationStartingEvent {

    public MyApplicationListener(SpringApplication application, String[] args) {
        super(application, args);
        System.out.println("我最快？");
    }


}
