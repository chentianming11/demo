package com.lianjia.util;


import com.lianjia.exception.AppException;

import java.util.Objects;

/**
 * Created by chen on 2018/3/21.
 */
public final class AppAssert {

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


}
