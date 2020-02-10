package org.uma.external.jvlink.client.test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JvlinkTest {
    private static final String JVLINK_DLL = "JVDTLab.JVLink.1";
    private ActiveXComponent activeXComponent = new ActiveXComponent(JVLINK_DLL);


    @Test
    public void test() {
        try {
            jvInit("UNKNOWN");
            jvOpen("RACE", "20190701000000", 4);
        } finally {
            jvClose();
        }
    }


    public void jvInit(String sid) {
        Variant variant = Dispatch.call(activeXComponent, "JVInit", sid);
        //Log.info(String.format("JRA-VANサーバにアクセスする際に %s をUserAgentとして使用します。", sid));
        System.out.println(variant);
        System.out.println(variant.getInt());
        variant.safeRelease();
    }


    public void jvOpen(String dataSpec, String fromTime, long option) {
        Variant readCount = getRefVariant(Integer.class, "0");
        Variant downloadCount = getRefVariant(Integer.class, "0");
        Variant lastFileTimeStamp = getRefVariant(String.class);
        Variant variant = Dispatch.call(activeXComponent, "JVOpen", dataSpec, fromTime, option, readCount, downloadCount, lastFileTimeStamp);
        System.out.println(variant);
        System.out.println(variant.getInt());
        System.out.println(readCount.getIntRef());
        System.out.println(downloadCount.getIntRef());
        System.out.println(lastFileTimeStamp.getStringRef());
        variant.safeRelease();
    }

    public void jvClose() {
        Variant variant = Dispatch.call(activeXComponent, "JVClose");
        System.out.println(variant);
        System.out.println(variant.getInt());
        variant.safeRelease();
    }


    private static Variant getRefVariant(Class<?> type, Object... parameters) {
        return new Variant(newInstance(type, parameters), true);
    }

    public static <T> Constructor<T> getConstructor(Class<T> target, Class<?>... parameterTypes) {
        try {
            return target.getConstructor(parameterTypes);
        } catch (SecurityException e) {
            throw e;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> target) {
        return getConstructor(target, new Class<?>[]{});
    }

    public static <T> T newInstance(Class<T> target, Object... parameters) {
        Constructor<T> constructor = getConstructor(target, convertTypeArray(parameters));
        try {
            return constructor.newInstance(parameters);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> target) {
        return newInstance(target, new Object[]{});
    }

    private static Class<?>[] convertTypeArray(Object[] parameters) {
        Class<?>[] classArray = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classArray[i] = parameters[i].getClass();
        }
        return classArray;
    }


}
