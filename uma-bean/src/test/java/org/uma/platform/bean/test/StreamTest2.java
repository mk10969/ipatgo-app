package org.uma.platform.bean.test;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest2 {


    @Test
    @Tag("File 操作")
    public void test() throws IOException {
        // Filesのオブジェクトは、client側で、close処理を書く必要がある。
        try (Stream<Path> path = Files.list(Paths.get("."))) {
            path.forEach(System.out::println);
        }
    }

    @Test
    public void test1() {
        try (DirectoryStream<Path> path = Files.newDirectoryStream(Paths.get("."))) {
            path.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws IOException {
        try (Stream<Path> pathStream = Files.walk(Paths.get("."))) {
            pathStream.map(path -> path.toFile())
                    .filter(file -> file.getName().endsWith(".java"))
                    .forEach(System.out::println);
        }
    }

    @Test
    public void test3() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        list.stream()
                .flatMap(i -> Stream.generate(() -> i).limit(i))
                .forEach(System.out::print);

        IntStream.range(0, 4).boxed()
                .flatMap(i -> IntStream.range(0, 4).boxed().map(j -> i + ":" + j))
                .forEach(System.out::println);
    }

    @Test
    public void test4() throws IOException, InterruptedException {
        final WatchService watchService = Paths.get(".")
                .getFileSystem()
                .newWatchService();

        final WatchKey watchKey = watchService.poll(1, TimeUnit.MINUTES);

        if (watchKey != null) {
            watchKey.pollEvents().stream()
                    .forEach(event -> System.out.println(event.context()));
        }
    }

}
