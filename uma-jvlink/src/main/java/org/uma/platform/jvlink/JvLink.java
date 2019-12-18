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


public abstract class JvLink {

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
    private static <T extends JvContent> List<T> builder(
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
     * Stream用のcloseメソッド
     */
    public void close() {
        JvLink.close();
    }

    /**
     * Publisherオブジェクトを生成する。
     * このPublisherの処理が終わると、closeし、スレッドを開放する
     * →ダメだった・・・・(´・ω・｀)
     *
     * @param function
     * @param <T>
     * @return
     */
    @Deprecated
    private static <T extends JvContent> Flux<T> publisher(
            final Function<JvLinkWrapper, Flux<T>> function) {
        Objects.requireNonNull(function);
        LOCK.lock();
        try {  // クラスロックをかけて、closeまで次のスレッドのアクセスを防ぐ。
            return function.apply(JvLink)
                    .publishOn(Schedulers.single()) //内部処理はシングルスレッドで行う。（仕様通り）
                    .doOnCancel(JvLink::close)
                    .doOnTerminate(JvLink::close); // completion or error
        } finally {
            LOCK.unlock();
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


    public static Stream<JvStringContent> readStream(
            final StoredOpenCondition condition,
            final LocalDateTime fromTime,
            final Option option) {

        return JvLink.init()
                .open(condition, fromTime, option)
                .read(condition)
                .stream()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()));
    }

    public static Stream<JvStringContent> readStream(
            final RealTimeOpenCondition condition,
            final RealTimeKey rtKey) {

        return JvLink.init()
                .rtOpen(condition, rtKey)
                .read(condition)
                .stream()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()));
    }


    @Deprecated
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

    @Deprecated
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
