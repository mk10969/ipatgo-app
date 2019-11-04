package org.uma.daiwaScarlet.test;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Test;
import org.uma.daiwaScarlet.configuration.JvLinkModelMapperConfiguration;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestReflection {


    @Test
    void test_パッケージ配下のクラス名取得() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Set<Class<?>> allClasses = ClassPath.from(loader)
                .getTopLevelClasses("org.uma.daiwaScarlet.code").stream()
                .map(info -> info.load())
                .collect(Collectors.toSet());

        allClasses.stream()
                .map(i -> i.getSimpleName())
                .forEach(System.out::println);

    }


    @Test
    void test_特定のクラスのメソッド一覧取得() {
        Stream.of(JvLinkModelMapperConfiguration.class.getDeclaredFields())
                .map(i -> i.getName())
                .map(i -> i.substring(2))
                .forEach(System.out::println);
    }

    @Test
    void test_差分比較() throws IOException {

        Set<String> fields = Stream.of(JvLinkModelMapperConfiguration.class.getDeclaredFields())
                .map(i -> i.getName())
                .map(i -> i.substring(2))
                .collect(Collectors.toSet());

        ClassPath.from(Thread.currentThread().getContextClassLoader())
                .getTopLevelClasses("org.uma.daiwaScarlet.code")
                .stream()
                .map(ClassPath.ClassInfo::load)
                .map(Class::getSimpleName)
//                .peek(System.out::println)
//                .peek(i-> System.out.println("------"))
                .filter(i -> !fields.contains(i))
                .map(className -> te(className))
                .forEach(System.out::println);

    }

    @Test
    void test_フィールドを全てマッパーにつめる() {

        Stream.of(JvLinkModelMapperConfiguration.class.getDeclaredFields())
                .map(i -> i.getName())
                .map(i -> tomapperAdd(i))
                .forEach(System.out::println);
    }


    private String tomapperAdd(String name) {
        String temp = "modelMapper.addConverter(%s);";
        return String.format(temp, name);

    }

    private String te(final String className) {
        String template =
                "    private static final Converter<String, %1$s> to%1$s = new AbstractConverter<String, %1$s>() {\n" +
                        "        @Override\n" +
                        "        protected %1$s convert(String source) {\n" +
                        "            return %1$s.of(source);\n" +
                        "        }\n" +
                        "    };\n";
        return String.format(template, className);
    }

}
