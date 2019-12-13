package org.uma.platform.jvlink;

import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.RealTimeKey;
import org.uma.platform.common.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.jvlink.response.JvContent;
import org.uma.platform.jvlink.response.JvStringContent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;


public abstract class JvLink {

    private static final JvLinkWrapper jvlink = new JvLinkWrapper();

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
     * クライアント側（このクラス）で、ロックをかけて単一スレッドで直列に処理を実行する。
     * また、このクラスを利用する側では、必ずtry-resource文を用いて、
     * close処理を忘れないようにすること。
     *
     * @param function
     * @param <T>
     * @return
     */
    public static <T extends JvContent> Stream<T> builder(
            final Function<JvLinkWrapper, Stream<T>> function) {
        Objects.requireNonNull(function);
        synchronized (jvlink) {
            return function.apply(jvlink)
                    .onClose(jvlink::close);
        }
    }

    /**
     * Publisherオブジェクトを生成する。
     * このPublisherの処理が終わると、closeし、スレッドを開放する
     *
     * @param function
     * @param <T>
     * @return
     */
    public static <T extends JvContent> Flux<T> publisher(
            final Function<JvLinkWrapper, Flux<T>> function) {
        Objects.requireNonNull(function);
        synchronized (jvlink) {  // クラスロックをかけて、closeまで次のスレッドのアクセスを防ぐ。
            return function.apply(jvlink)
                    .publishOn(Schedulers.single()) //内部処理はシングルスレッドで行う。（仕様通り）
                    .doOnCancel(jvlink::close)
                    .doOnTerminate(jvlink::close); // completion or error
        }
    }

    public static Stream<JvStringContent> lines(
            final StoredOpenCondition condition,
            final LocalDateTime fromTime,
            final Option option) {
        return builder(jvLink -> jvLink
                .init()
                .open(condition, fromTime, option)
                .read(condition)
                .stream()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))

        );
    }

    public static Stream<JvStringContent> lines(
            final RealTimeOpenCondition condition,
            final RealTimeKey rtKey) {
        return builder(jvLink -> jvLink
                .init()
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
        return publisher(jvLink -> jvLink
                .init()
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
        return publisher(jvLink -> jvLink
                .init()
                .rtOpen(condition, rtKey)
                .read(condition)
                .publish()
                .filter(content -> content.getLine()
                        .startsWith(condition.getRecordType().getCode()))
        );
    }

    /**
     * TODO: 改修予定
     *
     * @param filePath
     * @param cs
     * @param line
     */
    public static void writer(
            final Path filePath,
            final Charset cs,
            final String line) {
        Objects.requireNonNull(line);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, cs, StandardOpenOption.APPEND)) {
            writer.write(line, 0, line.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static void createFilePath(final Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace(); //TODO
            }
        }
    }

    public Mono<Void> checkFilePath(
            final Path filePath,
            final Charset cs,
            final String line) {
        return Mono.create(sink -> {
            Objects.requireNonNull(line);
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, cs, StandardOpenOption.APPEND)) {
                writer.write(line, 0, line.length());
                writer.newLine();
                sink.success();
            } catch (IOException e) {
                e.printStackTrace();
                sink.error(e);
            }
        });
    }


}
