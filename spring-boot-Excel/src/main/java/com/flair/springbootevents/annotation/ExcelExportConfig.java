package com.flair.springbootevents.annotation;


import com.flair.springbootevents.excel.DefaultTransformer;
import com.flair.springbootevents.excel.Transformer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
* excel导出注解
* */
@Retention(RetentionPolicy.RUNTIME )
public @interface ExcelExportConfig {
    /*
    * 导出excel对应表头名称
    * */
    String name();
    /*
    * 有内嵌对象是，可以设置外部对象忽略名称
    * */
    boolean ignoreName() default false;
    /*
    * 只对Date类型及其子类生效
    * */
    String format() default "yyyy-MM-dd HH:mm:ss";

    Class<? extends Transformer> transfomer() default DefaultTransformer.class;

    int width() default -1;
}
