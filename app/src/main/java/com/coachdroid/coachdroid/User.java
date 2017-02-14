package com.coachdroid.coachdroid;

@FunctionalInterface
public interface User<T> {
    void use(T obj);
}
