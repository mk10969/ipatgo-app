package org.uma.external.jvlink.response;

import org.junit.jupiter.api.Test;
import org.uma.external.jvlink.Calzone;

import java.lang.reflect.Constructor;

class JvSimpleResultTest {

    @Test
    public void test() {
        Class<?> clazz1 = JvSimpleResult.Builder.class;
        Class<?> clazz = Integer.class;
        Class<?> aaa = Calzone.Builder.class;

        int num = 0;

//        ClassUtil.newInstance(clazz, num);
        Object[] objects = {num};
        try {
            Constructor<?> constructor = clazz1.getConstructor(convertTypeArray(objects));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test8() {
        Byte byteData = Byte.valueOf("123");
        Object[] objects = {byteData};
        convertTypeArray(objects);

    }

    private static Class<?>[] convertTypeArray(Object[] parameters) {
        Class<?>[] classArray = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class<?> once = parameters[i].getClass();
            // int -> Integer.classになるため、
            // コンストラクタのシグネチャが異なっていたため、しくっていた。
            System.out.println(once);
            classArray[i] = once;
        }
        return classArray;
    }


}