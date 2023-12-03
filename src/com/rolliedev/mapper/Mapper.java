package com.rolliedev.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
