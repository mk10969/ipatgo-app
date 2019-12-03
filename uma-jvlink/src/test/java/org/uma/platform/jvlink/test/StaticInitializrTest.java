package org.uma.platform.jvlink.test;

public class StaticInitializrTest {

    private static final String once;

    static {
        once = "aaa";
    }

    public StaticInitializrTest() {
    }

    public static String getOnce(){
        return once;
    }

}