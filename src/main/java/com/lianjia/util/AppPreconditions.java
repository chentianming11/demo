package com.lianjia.util;


import com.lianjia.exception.AppException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/**
 * Created by chen on 2018/2/27.
 */
public class AppPreconditions {

    public static <T> T checkNotNull(T reference, @NullableDecl Object errorMessage) {
        if (reference == null) {
            throw new AppException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
