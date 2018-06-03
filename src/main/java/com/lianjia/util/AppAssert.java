package com.lianjia.util;


import com.lianjia.exception.AppException;

import java.util.Date;
import java.util.Objects;


/**
 * @author chenTianMing
 */
public abstract class AppAssert {

    public static void assertNotNull(Object o, String errorMessage){
        if (o == null){
            throw new AppException(errorMessage);
        }
    }

    public static void assertNull(Object o, String errorMessage){
        if (o != null){
            throw new AppException(errorMessage);
        }
    }

    public static void assertEquals(Object o1, Object o2 , String errorMessage){
        if (!Objects.equals(o1, o2)){
            throw new AppException(errorMessage);
        }
    }

    public static void assertNotEquals(Object o1, Object o2 , String errorMessage){
        if (Objects.equals(o1, o2)){
            throw new AppException(errorMessage);
        }
    }

    public static void assertDateLessThan(Date o1, Date o2 , String errorMessage){
        if (o1 == null || o2 == null){
            return;
        }
        int i = o1.compareTo(o2);
        if (i > 0){
            throw new AppException(errorMessage);
        }
    }


    public static void assertStringMoreThan(String remark, int i, String errorMessage) {
        if (Objects.nonNull(remark) && remark.length() < i){
            throw new AppException(errorMessage);
        }
    }

    public static void assertTrue(boolean b, String errorMessage) {
        if (b == false){
            throw new AppException(errorMessage);
        }
    }

    public static void assertFalse(boolean b, String errorMessage) {
        if (b == true){
            throw new AppException(errorMessage);
        }
    }


}
