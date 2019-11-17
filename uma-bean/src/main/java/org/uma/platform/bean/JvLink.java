package org.uma.platform.bean;

import org.uma.platform.bean.response.JvContent;
import org.uma.platform.bean.response.JvStringContent;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.RealTimeKey;
import org.uma.platform.common.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.config.condition.StoredOpenCondition;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JvLink {

    private static final JvLinkWrapper jvlink = new JvLinkWrapper();

    private JvLink() {
    }

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
     * クライアント側（このクラス）で、ロックをかけて直列に処理を実行する。
     * また、このクラスを利用する側では、必ずtry-resource文を用いて、
     * close処理を忘れないようにすること。
     *
     * @param function
     * @param <T>
     * @return
     */
    public static <T extends JvContent> Stream<T> builder(final Function<JvLinkWrapper, Stream<T>> function) {
        Objects.requireNonNull(function);
        synchronized (jvlink) {
            return function.apply(jvlink).onClose(jvlink::close);
        }
    }

    public static Stream<JvStringContent> lines(final StoredOpenCondition condition,
                                                final LocalDateTime fromTime,
                                                final Option option) {
        return builder(jvLink ->
                jvLink.init()
                        .open(condition, fromTime, option)
                        .read(condition)
                        .stream()
                        .filter(content ->
                                condition.getRecordType().getCode().startsWith(content.getLine())
                        )
        );
    }

    public static Stream<JvStringContent> lines(final RealTimeOpenCondition condition,
                                                final RealTimeKey rtKey) {
        return builder(jvLink ->
                jvLink.init()
                        .rtOpen(condition, rtKey)
                        .read(condition)
                        .stream()
                        .filter(content ->
                                condition.getRecordType().getCode().startsWith(content.getLine())
                        )
        );
    }

    public static List<JvStringContent> readAllLines(final StoredOpenCondition condition,
                                                     final LocalDateTime fromTime,
                                                     final Option option) {
        return lines(condition, fromTime, option).collect(Collectors.toList());
    }

    public static List<JvStringContent> readAllLines(final RealTimeOpenCondition condition,
                                                     final RealTimeKey rtKey) {
        return lines(condition, rtKey).collect(Collectors.toList());
    }

    public static void writer(final Path filePath, final Charset cs, final String line) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Objects.requireNonNull(line);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, cs, StandardOpenOption.APPEND)) {
            writer.write(line, 0, line.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

}
