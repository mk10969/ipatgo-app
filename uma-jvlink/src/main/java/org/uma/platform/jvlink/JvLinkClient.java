package org.uma.platform.jvlink;

import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.RealTimeKey;
import org.uma.platform.common.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.jvlink.response.JvContent;
import org.uma.platform.jvlink.response.JvStringContent;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

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


//    // Thread Safe Singleton.
//    public static JvLink getInstance() {
//        return JvLinkHolder.INSTANCE;
//    }
//
//    private static class JvLinkHolder {
//        // static final 最強
//        private static final JvLink INSTANCE = new JvLink();
//    }


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

    /**
     * Publisherオブジェクトを生成する。
     * このPublisherの処理が終わると、closeする
     */
    private static <T extends JvContent<?>> Flux<T> publisher(
            final Function<JvLinkWrapper, Flux<T>> function) {
        Objects.requireNonNull(function);
        return function.apply(JvLink)
                .publishOn(Schedulers.immediate()) //内部読み取り処理はシングルスレッド
                .doOnCancel(JvLink::close)
                .doOnTerminate(JvLink::close); // completion or error
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


    public static Stream<JvStringContent> readStream(
            final StoredOpenCondition condition,
            final LocalDateTime fromTime,
            final Option option) {
        return streamBuilder(jvLink -> jvLink.init()
                .open(condition, fromTime, option)
                .read(condition)
                .stream()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))
        );
    }

    public static Stream<JvStringContent> readStream(
            final RealTimeOpenCondition condition,
            final RealTimeKey rtKey) {
        return streamBuilder(jvLink -> jvLink.init()
                .rtOpen(condition, rtKey)
                .read(condition)
                .stream()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))
        );
    }


    public static Flux<JvStringContent> readFlux(
            final StoredOpenCondition condition,
            final LocalDateTime fromTime,
            final Option option) {
        return publisher(jvLink -> jvLink.init()
                .open(condition, fromTime, option)
                .read(condition)
                .publish()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))
        );
    }

    public static Flux<JvStringContent> readFlux(
            final RealTimeOpenCondition condition,
            final RealTimeKey rtKey) {
        return publisher(jvLink -> jvLink.init()
                .rtOpen(condition, rtKey)
                .read(condition)
                .publish()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))
        );
    }

}
