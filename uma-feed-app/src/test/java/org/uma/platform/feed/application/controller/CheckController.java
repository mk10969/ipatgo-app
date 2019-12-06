package org.uma.platform.feed.application.controller;

import org.junit.jupiter.api.Test;
import org.uma.platform.feed.application.component.ReflectionUtils;

import java.util.Arrays;
import java.util.List;

public class CheckController {

    @Test
    void create_impl() {

        List<String> packages = Arrays.asList(
                "org.uma.platform.feed.application.repository.impl",
                "org.uma.platform.common.model"
        );
        packages.stream()
                .flatMap(packageName -> ReflectionUtils.getClassesFrom(packageName)
                        .stream()
                        .map(Class::getSimpleName)
                        .filter(i -> !i.contains("Test")))
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    void create() {
        ReflectionUtils.getClassesFrom("org.uma.platform.common.model")
                .stream()
                .map(i -> template(i.getSimpleName()))
                .forEach(System.out::println);
    }


    private String template(String modelName) {
        String str =
                "@RestController\n" +
                        "@RequestMapping(\"/api/bt\")\n" +
                        "public class %1$sController {\n" +
                        "    private final JvStored%1$sRepository jvRepository;\n" +
                        "\n" +
                        "    public %1$sController(JvStored%1$sRepository jvRepository) {\n" +
                        "        this.jvRepository = jvRepository;\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    @GetMapping(\"/%2$s\")\n" +
                        "    public Flux<%1$s> readFluxOnThisWeek() {\n" +
                        "        return Mono\n" +
                        "                .defer(() -> Mono.just(lastWeek()))\n" +
                        "                .flatMapMany(i -> jvRepository.readFlux(i, Option.THIS_WEEK));\n" +
                        "    }\n" +
                        "\n" +
                        "    @GetMapping(\"/%2$s/{epochSecond}\")\n" +
                        "    public Flux<%1$s> readFluxOnStandard(@PathVariable(value = \"epochSecond\") Long epochSecond) {\n" +
                        "        return Mono\n" +
                        "                .just(tolocalDateTime(epochSecond))\n" +
                        "                .map(DateUtil::within3years)\n" +
                        "                .flatMapMany(i -> jvRepository.readFlux(i, Option.STANDARD));\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "}\n";


        return String.format(str, modelName, modelName.toLowerCase());

    }


}
