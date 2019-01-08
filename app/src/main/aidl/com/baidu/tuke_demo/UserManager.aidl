// UserManager.aidl
package com.baidu.tuke_demo;

// Declare any non-default types here with import statements

interface UserManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getName();

    String getOtherName();

}
