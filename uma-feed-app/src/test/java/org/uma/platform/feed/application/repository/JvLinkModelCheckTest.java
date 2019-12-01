package org.uma.platform.feed.application.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.feed.application.component.ReflectionUtils;
import org.uma.platform.feed.application.configuration.JvLinkRecordSpecConfiguration;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JvLinkModelCheckTest {

    @Autowired
    private Map<String, JvLinkRecordSpecConfiguration.RecordSpecItems> recordSpecs;

    @Autowired
    private EnumMap<RecordSpec, Class<?>> recordSpecClass;


    @Test
    void check() {
        // ={}も、名前は、完全一致
        Flux.fromStream(findModelClass())
                .flatMap(clazz -> Flux.fromIterable(sortedClassField(clazz))
                        .zipWith(Flux.fromIterable(sortedProperies(findOf(clazz))))
                        .filter(t -> !t.getT1().equals(t.getT2()))
                )
                .subscribe(System.out::println);
    }

    private RecordSpec findOf(Class<?> clazz) {
        return recordSpecClass.entrySet().stream()
                .filter(i -> i.getValue() == clazz)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private Stream<Class<?>> findModelClass() {
        return ReflectionUtils.getClassesFrom("org.uma.platform.common.model")
                .stream()
                .peek(i -> System.out.println(i.getSimpleName()));
    }

    private List<String> sortedClassField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    private List<String> sortedProperies(RecordSpec recordSpec) {
        return recordSpecs.get(recordSpec.getCode()).getRecordItems()
                .stream()
                .map(JvLinkRecordSpecConfiguration.RecordSpecItems.RecordItem::getColumn)
                .sorted()
                .collect(Collectors.toList());
    }


}
