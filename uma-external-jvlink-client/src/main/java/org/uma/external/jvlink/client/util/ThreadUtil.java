package org.uma.external.jvlink.client.util;

public class ThreadUtil {

    private ThreadUtil() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}