package org.uma.platform.feed.application.controller;

import org.junit.jupiter.api.Test;
import org.uma.platform.feed.application.component.ReflectionUtils;

import java.util.Arrays;
import java.util.List;

public class CheckController {

    @Test
    void create_impl() {

        List<String> packages = Arrays.asList(
                "org.uma.platform.feed.application.service",
                "org.uma.platform.feed.application.repository.impl",
                "org.uma.platform.common.model"
        );

        packages.stream()
                .map(packageName -> ReflectionUtils.getClassesFrom(packageName)
                        .stream()
                        .map(i -> i.getSimpleName())
                        .filter(i -> !i.contains("Test"))
                        .count())
                .forEach(System.out::println);

    }

}
