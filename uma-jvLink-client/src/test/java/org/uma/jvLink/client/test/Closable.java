package org.uma.jvLink.client.test;

public class Closable implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("Auto closeしました。");
    }

    public void doSomething() {
        System.out.println("do something !!!");

    }

    public void thowError() throws Exception {
        System.out.println("Exceptionを発生させます。");
        throw new Exception("Exception!!!");
    }

    public void runTimeError() {
        System.out.println("RuntimeExceptionを発生させます。");
        throw new RuntimeException("RuntimeException!!!");

    }
}
