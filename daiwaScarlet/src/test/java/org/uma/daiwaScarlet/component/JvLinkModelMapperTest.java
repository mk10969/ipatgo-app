package org.uma.daiwaScarlet.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.daiwaScarlet.model.HorseRacingDetailsModel;
import org.uma.daiwaScarlet.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JvLinkModelMapperTest {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Test
    void test_() {
        assertEquals(1, 1);
    }

    @Test
    void test_HorseRacingDetailsModelを変換() throws InvocationTargetException, IllegalAccessException {

        Method method = Arrays.stream(jvLinkModelMapper.getClass().getDeclaredMethods())
                .filter(i -> i.getName().equals("findOne"))
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        method.setAccessible(true);
        Object a = method.invoke(jvLinkModelMapper, HorseRacingDetailsModel.class);
        System.out.println(a);
    }

    // 大丈夫そうでした。
    @Test
    void test_全モデル() {
        String packageName = "org.uma.daiwaScarlet.model";
        Arrays.stream(jvLinkModelMapper.getClass().getDeclaredMethods())
                .filter(i -> i.getName().equals("findOne"))
                .peek(i -> i.setAccessible(true))
                .flatMap(mathod -> ReflectionUtils.getClassesFrom(packageName)
                        .stream()
                        .filter(j -> !j.getName().contains("Test"))
                        .map(clazz -> invoke(jvLinkModelMapper, mathod, clazz)))
                .forEach(System.out::println);

    }

    // 小細工
    private static Object invoke(Object obj, Method method, Object args) {
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return new IllegalArgumentException();
        }
    }

}