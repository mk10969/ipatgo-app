package org.uma.external.controller;


import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.uma.external.jvlink.response.JvStringContent;
import org.uma.external.jvlink.util.ByteUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public class BaseController {

    protected List<String> getAll(Supplier<Stream<JvStringContent>> supplier) {
        Objects.requireNonNull(supplier);

        return supplier.get()
                .map(JvStringContent::getLine)
                .map(ByteUtil::toByte)
                .map(bytes -> Base64.getEncoder().encode(bytes))
                .map(encoded -> new String(encoded, StandardCharsets.ISO_8859_1))
                .peek(log::info)
                .collect(ImmutableList.toImmutableList());
    }

    protected String getOne(Supplier<Stream<JvStringContent>> supplier) {
        List<String> mono = getAll(supplier);

        if (mono.size() == 1) {
            return mono.get(0);
        } else {
            throw new IllegalStateException("Sizeが、1ではありません。 data: " + mono);
        }
    }

}
