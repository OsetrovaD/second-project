package com.osetrova.project.converter;

public interface Converter<R, T> {

    R convert(T object);
}
