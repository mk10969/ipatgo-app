package org.uma.extarnal.jvlink.server.controller;


import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.uma.extarnal.jvlink.client.response.JvStringContent;
import org.uma.extarnal.jvlink.client.util.ByteUtil;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public class BaseController {

    protected List<String> converter(Supplier<Stream<JvStringContent>> supplier) {
        Objects.requireNonNull(supplier);

        // TODO: byteArrayとStringどっちをログに吐くか。。
        return supplier.get()
                .map(JvStringContent::getLine)
                .map(ByteUtil::toByte)
                .map(bytes -> Base64.getEncoder().encode(bytes))
                .map(String::new)
                .peek(log::info)
                .collect(ImmutableList.toImmutableList());
    }

}
