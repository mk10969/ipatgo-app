package org.uma.external.jvlink.client.test;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

public class LambdaTest4 {

    @Test
    void test() {
        FluentMailer.send(mailer ->
                mailer.setFrom("aaaaa")
                        .setBody("bbbb")
                        .setSubject("ccccc")
                        .setTo("dddd")
        );
    }

    @Test
    void test2() {
        FluentMailer mailer = FluentMailer.create();
        System.out.println(mailer);

    }


    public static class FileWriterEAM {
        private final FileWriter writer;

        private FileWriterEAM(final String filename) throws IOException {
            this.writer = new FileWriter(filename);
        }

        private void close() throws IOException {
            System.out.println("closeが呼ばれました。");
            writer.close();
        }

        public void writeStuff(final String message) throws IOException {
            writer.write(message);
        }

        public static void use(final String filename,
                               final UseInstance<FileWriterEAM, IOException> block) throws IOException {
            final FileWriterEAM writer = new FileWriterEAM(filename);
            try {
                block.accept(writer);
            } finally {
                writer.close();
            }
        }

    }

    @FunctionalInterface
    public interface UseInstance<T, X extends Throwable> {
        void accept(T instance) throws X;
    }


    @Test
    void test3() throws IOException {

        String aaa = "aaaa";
        FileWriterEAM.use("aaa", writer -> writer.writeStuff("message!!!"));
        FileWriterEAM.use("aaa", writer -> {
            writer.writeStuff("message!!!");
            writer.writeStuff("message!!!");
            writer.writeStuff(aaa);
        });


    }


}
