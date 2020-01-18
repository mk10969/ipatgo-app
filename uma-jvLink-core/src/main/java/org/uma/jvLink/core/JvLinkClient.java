package org.uma.jvLink.core;

import org.uma.jvLink.core.config.option.Option;
import org.uma.jvLink.core.config.option.RealTimeKey;
import org.uma.jvLink.core.config.condition.RealTimeOpenCondition;
import org.uma.jvLink.core.config.condition.StoredOpenCondition;
import org.uma.jvLink.core.response.JvContent;
import org.uma.jvLink.core.response.JvStringContent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class JvLinkClient {

    private static final JvLinkWrapper JvLink = new JvLinkWrapper();

    private static final Lock LOCK = new ReentrantLock();

    /**
     * JvLinK側がマルチスレッドによる同時アクセスに対応していないため、
     * クライアント側（このクラス）で、ロックをかけて排他制御を行う。
     */
    private static <T extends JvContent<?>> List<T> builder(
            final Function<JvLinkWrapper, Stream<T>> function) {
        Objects.requireNonNull(function);
        synchronized (JvLink) {
            try {
                return function.apply(JvLink)
                        .collect(Collectors.toList());
            } finally {
                JvLink.close();
            }
        }
    }

    public static List<JvStringContent> readLines(
            final StoredOpenCondition condition,
            final LocalDateTime fromTime,
            final Option option) {
        return builder(jvLink -> jvLink.init()
                .open(condition, fromTime, option)
                .read(condition)
                .stream()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))
        );
    }

    public static List<JvStringContent> readLines(
            final RealTimeOpenCondition condition,
            final RealTimeKey rtKey) {
        return builder(jvLink -> jvLink.init()
                .rtOpen(condition, rtKey)
                .read(condition)
                .stream()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))
        );
    }


    /**
     * Streamに onClose()メソッドを付与しているので、
     * 利用側で、try-with-resourceで括ってください。
     */
    private static <T extends JvContent<?>> Stream<T> streamBuilder(
            final Function<JvLinkWrapper, Stream<T>> function) {
        Objects.requireNonNull(function);
        return function.apply(JvLink)
                .onClose(JvLink::close);
    }

    public static Stream<JvStringContent> readForSetup(
            final StoredOpenCondition condition,
            final LocalDateTime fromTime,
            final Option option) {
        return streamBuilder(jvLink -> jvLink.init()
                .open(condition, fromTime, option)
                .read(condition)
                .stream()
        );
    }

}
