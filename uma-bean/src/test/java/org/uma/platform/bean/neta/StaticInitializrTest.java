package org.uma.platform.bean.neta;

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
