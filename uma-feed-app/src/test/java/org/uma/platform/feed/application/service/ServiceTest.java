package org.uma.platform.feed.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.util.ReflectionUtils;


class ServiceTest {

    @Test
    void create_impl() {
        ReflectionUtils.getClassesFrom("org.uma.platform.common.model").stream()
                .filter(i -> i != RacingDetails.class)
                .filter(i -> i != RaceRefund.class)
                .map(this::template)
                .forEach(System.out::println);
    }

    private String template(Class<?> clazz) {
        String tmp = "@Service\n" +
                "@RequiredArgsConstructor\n" +
                "public class %1$sService {\n" +
                "\n" +
                "    private final JvStored%1$sRepository jvRepository;\n" +
                "\n" +
                "\n" +
                "    public List<%1$s> findAllOnThisWeek(ZonedDateTime dateTime) {\n" +
                "        return jvRepository.findAll(dateTime, Option.THIS_WEEK);\n" +
                "    }\n" +
                "\n" +
                "    public List<%1$s> findAllOnStandard(ZonedDateTime dateTime) {\n" +
                "        return jvRepository.findAll(dateTime, Option.STANDARD);\n" +
                "    }\n" +
                "    \n" +
                "}";

        return String.format(tmp, clazz.getSimpleName());
    }

}