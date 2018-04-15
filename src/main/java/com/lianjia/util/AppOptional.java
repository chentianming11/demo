package com.lianjia.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by chen on 2018/3/22.
 */
public final class AppOptional<T> {

    private static final AppOptional<?> EMPTY = new AppOptional<>();

    private final T value;

    private AppOptional() {
        this.value = null;
    }

    public static<T> AppOptional<T> empty() {
        @SuppressWarnings("unchecked")
        AppOptional<T> t = (AppOptional<T>) EMPTY;
        return t;
    }

    private AppOptional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> AppOptional<T> of(T value) {
        return new AppOptional<>(value);
    }

    public static <T> AppOptional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }




}
