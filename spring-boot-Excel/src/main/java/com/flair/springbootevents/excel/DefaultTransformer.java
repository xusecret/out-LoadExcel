package com.flair.springbootevents.excel;


public class DefaultTransformer implements Transformer {
    @Override
    public Object transform(String fieldName, Object value) {
        return value;
    }
}
