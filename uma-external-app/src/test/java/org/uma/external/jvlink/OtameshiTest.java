package org.uma.external.jvlink;

import org.junit.jupiter.api.Test;
import org.uma.external.jvlink.response.JvOpenResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class OtameshiTest {

//    @Test
//    public void test1() {
//        Otameshi otameshi = new Otameshi();
//        System.out.println(otameshi.getInstance2("org.uma.vodka.jvlink.response.JvOpenResult"));
//    }
//
    @Test
    public void test1() {
        try {
            Class<?> clazz = Class.forName("org.uma.platform.jvlink.neta.jvlink.response.JvOpenResult");
            System.out.println(clazz);

        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public Object getInstance2(String clazz) {
        try {
            return Class.forName(clazz).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void test6() {
        Constructor<JvOpenResult> constructor = getConstructor2(JvOpenResult.class);
        System.out.println(constructor);
//
//        cons = c.getConstructor2(int.class);
//        System.out.println(cons);
//
//        cons = c.getConstructor2(String.class, BigInteger[].class);
//        System.out.println(cons);

    }

    private <T> Constructor<T> getConstructor2(Class<T> target, Class<?>... parameterTypes) {
        try {
            return target.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            System.out.println(e);
        }
        return null;
    }

    public void test2() {

    }


}