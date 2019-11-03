//package org.uma.vodka.jvlink;
//
//
//import org.junit.jupiter.api.Test;
//import org.uma.vodka.jvlink.config.RealTimeKey;
//import org.uma.vodka.jvlink.config.StoredOption;
//import org.uma.vodka.jvlink.config.condition.OpenCondition;
//import org.uma.vodka.jvlink.config.condition.RealTimeOpenCondition;
//import org.uma.vodka.jvlink.config.condition.StoredOpenCondition;
//import org.uma.vodka.jvlink.config.spec.RecordSpec;
//import org.uma.vodka.jvlink.config.spec.StoredDataSpec;
//import org.uma.vodka.jvlink.response.JvByteContent;
//
//import java.io.UnsupportedEncodingException;
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//import static org.uma.vodka.jvlink.config.StoredOption.THIS_WEEK;
//
//public class JvLinkManagerTest {
//
//    public static class testStream {
//
//        private final JvLinkManager JvLinkManager;
//        private final OpenCondition condition;
//
//        public testStream(OpenCondition condition) {
//            this.JvLinkManager = new JvLinkManager();
//            this.condition = condition;
//        }
//
//        public Stream<JvByteContent> read(ZonedDateTime time, StoredOption storedOption) {
//            try (Stream<JvByteContent> aaa = JvLinkManager.init()
//                    .open((StoredOpenCondition) condition, time, storedOption)
//                    .read(condition)
//                    .stream()
//                    .onClose(this::JvLinkClose)
//            ) {
//                return aaa;
//            }
//        }
//
//        public Stream<JvByteContent> read(RealTimeKey realTimeKey) {
//            try (Stream<JvByteContent> aaa = JvLinkManager.init()
//                    .rtOpen((RealTimeOpenCondition) condition, realTimeKey)
//                    .read(condition)
//                    .stream()
//                    .onClose(this::JvLinkClose)
//            ) {
//                return aaa;
//            }
//        }
//
//        private void JvLinkClose() {
//            JvLinkManager.close();
//        }
//    }
//
//
////    @Test
////    public void test9() {
////        JvLinkManager manager = new JvLinkManager();
////        StoredOpenCondition condition = new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA);
////        String fromdate = "20190801000000";
////
////        try {
////            Supplier<JvLinkReader<JvByteContent>> generator = () -> manager
////                    .init().open(condition, fromdate, StoredOption.THIS_WEEK).read(condition);
////
////            reader = generator.get();
////
////            for (; reader.hasNext(); ) {
////                JvByteContent result = reader.next();
////                System.out.println(result);
////            }
////        } finally {
////            manager.close();
////        }
////    }
//
//    @Test
//    public void test() {
//        JvLinkManager manager = new JvLinkManager();
//        StoredOpenCondition condition = new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA);
//        String fromdate = "20190801000000";
//
//        try {
//            manager.init().open(condition, fromdate, THIS_WEEK).read(condition)
//                    .forEach((i) -> {
//                        try {
//                            System.out.println(new String(i.getLine(), "SJIS"));
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                    });
//        } finally {
//            manager.close();
//        }
//
//    }
//
//    @Test
//    public void test10() {
//        List<Integer> aaa = new ArrayList<>(10);
//        aaa.forEach((a) -> System.out.println(a));
//        IntStream.range(0, 10).forEach(x -> System.out.println(x));
//    }
//
//    @Test
//    void test99() {
//        JvLinkManager manager = new JvLinkManager();
//        StoredOpenCondition condition = new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA);
//        String fromdate = "20190801000000";
//
//        try {
//            manager.init()
//                    .open(condition, fromdate, THIS_WEEK)
//                    .read(condition);
//        } finally {
//            manager.close();
//        }
//
//    }
//
////    @Test
////    void test111() {
////
////        StoredOpenCondition condition = new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA);
////        String fromdate = "20190801000000";
////
////        Stream<JvByteContent> reader = JvLinkManager.useJvlink(manager ->
////                manager.init()
////                        .open(condition, fromdate, THIS_WEEK)
////                        .read(condition)
////                        .stream()
////        );
////        System.out.println("今ここ");
////        reader.forEach(System.out::println);
////    }
//
////    @FunctionalInterface
////    public interface reader<U, R> {
////        R apply(U instance);
////    }
////
////    public static JvLinkReader<JvByteContent> useJvlink(
////            final reader<JvLinkManager, JvLinkReader<JvByteContent>> instance) {
////        JvLinkManager manager = new JvLinkManager();
////        try {
////            return instance.apply(manager);
////        } finally {
////            System.out.println("jvlink 閉じます");
////            manager.close();
////        }
////    }
//
//
//}
