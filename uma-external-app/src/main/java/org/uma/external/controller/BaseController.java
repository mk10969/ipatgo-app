package org.uma.external.controller;


import lombok.extern.slf4j.Slf4j;
import org.uma.external.jvlink.exception.JvLinkRuntimeException;
import org.uma.external.jvlink.response.JvStringContent;
import org.uma.external.jvlink.util.ByteUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class BaseController {

    protected Flux<String> getAll(Supplier<List<JvStringContent>> supplier) {
        // blockingする
        List<JvStringContent> jvStringContents = Objects.requireNonNull(supplier).get();

        return Flux.fromIterable(jvStringContents)
                .map(JvStringContent::getLine)
                .map(ByteUtil::toByte)
                .map(bytes -> Base64.getEncoder().encode(bytes))
                .map(encoded -> new String(encoded, StandardCharsets.ISO_8859_1))
                .doOnError(ex -> log.error("JvLinkError: ", ex))
                .onErrorResume(JvLinkRuntimeException.class, ex -> Mono.just(ex.getMessage()))
                ;
    }

    protected Mono<String> getOne(Supplier<List<JvStringContent>> supplier) {
        // blockingする
        List<String> mono = getAll(supplier).toStream().collect(Collectors.toList());

        if (mono.size() != 1) {
            throw new IllegalStateException("Sizeが、1ではありません。 data: " + mono);
        }
        return Mono.just(mono.get(0));
    }

}
