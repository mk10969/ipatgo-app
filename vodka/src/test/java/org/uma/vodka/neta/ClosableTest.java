package org.uma.vodka.neta;

import org.junit.jupiter.api.Test;

public class ClosableTest {

    private String test() {
        try (Closable closable = new Closable()) {
            closable.doSomething();
            String result = "戻り値返します。";
            return result;
        }
    }

    @Test
    public void test59() {
        System.out.println("test呼びます。");
        String reTrun = test();
        System.out.println(reTrun);
        System.out.println("test呼びました。");
    }

    @Test
    public void test1() {
        try (Closable closable = new Closable()) {
            closable.runTimeError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try (Closable closable = new Closable()) {
            closable.thowError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
