package org.uma.jvLink.server.controller;


import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.uma.jvLink.core.response.JvStringContent;
import org.uma.jvLink.core.util.ByteUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public class BaseController {

    protected List<byte[]> converter(Supplier<Stream<JvStringContent>> supplier) {
        Objects.requireNonNull(supplier);

        // TODO: byteArrayとStringどっちをログに吐くか。。
        return supplier.get()
                .map(JvStringContent::getLine)
                .peek(log::info)
                .map(ByteUtil::toByte)
                .collect(ImmutableList.toImmutableList());
    }

}
