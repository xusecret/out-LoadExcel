package com.flair.springbootevents.excel;

public interface Transformer {
    Object transform(String fieldName, Object value);
}
