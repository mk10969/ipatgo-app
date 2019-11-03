package org.uma.vodka.test;


import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.uma.vodka.test.NyPizza.Size.MEDIUM;
import static org.uma.vodka.test.Pizza.Topping.*;

public class clientBuilderPattern {

    @Test
    public void test() {
        NutritionFacts cocaCola = new NutritionFacts
                .Builder(240, 8)
                .calories(100)
                .build();

        System.out.println(cocaCola);

    }

    @Test
    public void test1() {
        NyPizza pizza = new NyPizza
                .Builder(MEDIUM)
                .addTopping(SAUSAGE)
                .addTopping(ONION)
                .build();

    }

    @Test
    public void test2() {
        Calzone pizza = new Calzone
                .Builder()
                .addTopping(SAUSAGE)
                .addTopping(HAM)
                .sauceInside()
                .build();

    }

    @Test
    public void test3() {
        Class<? extends Pizza> aa = Calzone.class;
        //System.out.println(aa);

        Class<?>[] bb = aa.getDeclaredClasses();

//        System.out.println(bb);

//        Stream<Field> stream3 = Stream.of(bb);
//        stream3.forEach(s -> System.out.println(s));

        Stream<Class<?>> stream1 = Arrays.stream(bb);

        stream1.forEach(s -> System.out.println(s));

//        for (Class<?> clazz: bb) {
//            if (clazz.equals(Pizza.Builder.class)){
//                System.out.println(clazz);
//
//            }else{
//                System.out.println("ない");
//            }

        Class<?> eee = aa.getDeclaredClasses()[0];
        Class<?> aaaaa = Calzone.Builder.class;

        if (true) {
            System.out.println("OK");
        } else {
            System.out.println("NG");

        }

    }

    @Test
    public void test5() {
        Class<?> aaa = Calzone.Builder.class;

        try {
            Object instance = aaa.getConstructor().newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }
}
