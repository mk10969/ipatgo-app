package org.uma.vodka.test;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class StreamTest1 {

    @Test
    public void test() {

        final List<BigDecimal> prices = Arrays.asList(
                new BigDecimal("10"),
                new BigDecimal("30"),
                new BigDecimal("17"),
                new BigDecimal("20"),
                new BigDecimal("15"),
                new BigDecimal("18"),
                new BigDecimal("45"),
                new BigDecimal("12")
        );

        final BigDecimal totalOfDiscountPrices = prices.stream()
                .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(totalOfDiscountPrices);

    }

    @Test
    public void test1() {

        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sala", "Scotto");
        friends.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        System.out.println(friends);

        List<String> startWithN = friends.stream()
                .filter(name -> name.startsWith("N"))
                .collect(toList());
        System.out.println(startWithN);


        List<String> startWithB = friends.stream()
                .filter(checkIfStartWith("B"))
                .collect(toList());
        System.out.println(startWithN);

        List<String> startWithR = friends.stream()
                .filter(startWithLetter2.apply("R"))
                .collect(toList());
        System.out.println(startWithN);


    }

    public static Predicate<String> checkIfStartWith(final String letter) {
        return name -> name.startsWith(letter);
    }

    // 上記は、下記に書き換え可能。
    final Function<String, Predicate<String>> startWithLetter = (String letter) -> {
        Predicate<String> checkStarts = name -> name.startsWith(letter);
        return checkStarts;
    };

    private final Function<String, Predicate<String>> startWithLetter2 = letter -> name -> name.startsWith(letter);


    @Test
    public void test5() {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sala", "Scotto");
        final Optional<String> aLongName = friends.stream()
                .reduce((name1, name2) -> name1.length() > name2.length() ? name1 : name2);
        aLongName.ifPresent(name -> System.out.println(name));

        final String SteveOrLonger = friends.stream()
                .reduce("Steve", (name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
        System.out.println(SteveOrLonger);

        System.out.println(friends.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "))
        );

    }

    @Test
    public void test12() {

        final List<Peaple> peaples = Arrays.asList(
                new Peaple("John", 20),
                new Peaple("Sara", 21),
                new Peaple("Jane", 21),
                new Peaple("Greg", 35)
        );

        List<Peaple> ascendingAge = peaples.stream()
                .sorted((p1, p2) -> p1.ageDifference(p2))
                .collect(toList());

        System.out.println(ascendingAge);

        // comparatorの再利用
        final Comparator<Peaple> compareAscending = (pealpe1, pealpe2) -> pealpe1.ageDifference(pealpe2);
        final Comparator<Peaple> compareDescending = compareAscending.reversed();

        List<Peaple> ascendingAge2 = peaples.stream()
                .sorted(compareAscending)
                .collect(toList());
        System.out.println(ascendingAge2);

        List<Peaple> descendingAge = peaples.stream()
                .sorted(compareDescending)
                .collect(toList());
        System.out.println(descendingAge);

        List<Peaple> ascendingName = peaples.stream()
                .sorted((p1, p2) -> p1.getAge().compareTo(p2.getAge()))
                .collect(toList());
        System.out.println(ascendingName);

        peaples.stream()
                .min((p1, p2) -> p1.ageDifference(p2))
                .ifPresent(System.out::println);

    }

    @Test
    @Tag("comparator interface")
    public void test8() {

        final List<Peaple> peaples = Arrays.asList(
                new Peaple("John", 20),
                new Peaple("Sara", 21),
                new Peaple("Jane", 21),
                new Peaple("Greg", 35)
        );

        final Function<Peaple, String> byName = p -> p.getName();
        final Function<Peaple, Integer> byAge = p -> p.getAge();

        peaples.stream()
                .sorted(Comparator.comparing(byAge).thenComparing(byName))
                .forEach(System.out::println);

        peaples.stream()
                .filter(peaple -> peaple.getAge() > 20)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        //  上記と等価。
        peaples.stream()
                .filter(peaple -> peaple.getAge() > 20)
                .collect(toList());

        Map<Integer, List<Peaple>> grouping = peaples.stream()
                .collect(Collectors.groupingBy(p -> p.getAge()));
        System.out.println(grouping);

        Map<Integer, List<String>> nameOfgroupByAge =
                peaples.stream()
                        .collect(Collectors.groupingBy(
                                Peaple::getAge,
                                Collectors.mapping(Peaple::getName, Collectors.toList()))
                        );
        System.out.println(nameOfgroupByAge);

        Map<Character, Optional<Peaple>> oldsstPeapleOfEachLetter =
                peaples.stream()
                        .collect(Collectors
                                .groupingBy(
                                        p -> p.getName().charAt(0),
                                        Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(p -> p.getAge())))
                                )
                        );
        System.out.println(oldsstPeapleOfEachLetter);


    }

}


