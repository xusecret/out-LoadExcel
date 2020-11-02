package com.flair.springbootevents.events;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class MyApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        boolean active = configurableApplicationContext.isActive();
        System.out.println(active);
        System.out.println("MyApplicationContextInitializer...initialize...执行了");
    }
}
