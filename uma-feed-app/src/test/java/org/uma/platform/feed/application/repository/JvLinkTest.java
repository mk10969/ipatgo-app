package org.uma.platform.feed.application.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.utils.lang.ThreadUtil;
import org.uma.platform.jvlink.JvLink;
import org.uma.platform.jvlink.response.JvStringContent;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JvLinkTest {

    @Autowired
    @Qualifier("RACE_RA")
    private StoredOpenCondition conditionRA;

    @Autowired
    @Qualifier("RACE_SE")
    private StoredOpenCondition conditionSE;

    @Autowired
    @Qualifier("RACE_HR")
    private StoredOpenCondition conditionHR;

    // THIS WEEK: TOKU,RACE,TCOV（DIFF系）,RCOV（DIFF系）,SNAPだけ。
    @Autowired
    @Qualifier("YSCH_YS") //これ情報としていらん。
    private StoredOpenCondition conditionYS;

    @Autowired
    @Qualifier("BLOD_SK")
    private StoredOpenCondition conditionSK;

    @Autowired
    @Qualifier("BLOD_BT")
    private StoredOpenCondition conditionBT;

    @Autowired
    @Qualifier("BLOD_HN")
    private StoredOpenCondition conditionHN;

    @Autowired
    @Qualifier("RACE_H1")
    private StoredOpenCondition conditionH1;

    @Autowired
    @Qualifier("RACE_JG")
    private StoredOpenCondition conditionJG;

    @Autowired
    @Qualifier("RACE_O1")
    private StoredOpenCondition conditionO1;

    @Autowired
    private Map<String, StoredOpenCondition> conditionMap;

    private final LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);


    @Test
    void test_JvLinkデータがオッズデータ種別のもの一括取得() {
        Flux.fromStream(conditionMap
                .entrySet()
                .stream()
                .filter(i -> i.getKey().contains("RACE_O")))
                .flatMap(i -> JvLink
                        .readFlux(i.getValue(), dateTime, Option.STANDARD)
                        .take(5))
                .subscribe(
                        i -> System.out.println(i.getLine()),
                        e -> e.printStackTrace(),
                        () -> System.out.println("完了")
                );
        ThreadUtil.sleep(6000L);
    }

    @Test
    void test_シングルスレッドでJvLink呼び出し() {
        JvLink.readFlux(conditionO1, dateTime, Option.STANDARD)
                .subscribe(
                        i -> System.out.println(i.getLine()),
                        e -> e.printStackTrace(),
                        () -> System.out.println("完了")
                );
        // filterロジックがダメでした。
        ThreadUtil.sleep(3000L);
    }

    @Test
    void test_マルチスレッドでJvLink呼び出し() {
        // 想定通りの動きをしてくれている。よしよし。（速い処理はできないね！仕方ない！）
        // ただし、クラスロック「synchronized (jvlink)」と、「.subscribeOn(Schedulers.single())」
        // のどちらかを取り除くと、203エラーが返ってくる。（JvLink側、マルチスレッド非対応）
        Flux.just(conditionSK, conditionHN)
                .subscribeOn(Schedulers.elastic())
                .log()
                .flatMap(i -> JvLink
                        .readFlux(i, dateTime, Option.STANDARD)
                        .map(JvStringContent::getLine))
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("完了")
                );
        ThreadUtil.sleep(10000L);
    }

    @Test
    void test_セットアップ用_BLOD() {
        Flux.just(conditionSK, conditionHN)
                .subscribeOn(Schedulers.elastic())
                .log()
                .flatMap(i -> JvLink
                        .readFlux(i, dateTime, Option.SETUP_WITHOUT_DIALOG)
                        .map(JvStringContent::getLine))
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("完了")
                );
        ThreadUtil.sleep(10000L);
    }

    @Test
    void test_JvLinkデータが空になる事象調査2() {
        JvLink.lines(conditionRA, dateTime, Option.THIS_WEEK)
                .forEach(i -> System.out.println(i.getLine()));
    }

    @Test
    void test_startWith() {
        // そりゃそうだよな。。はぁなんで逆転したんだろう。。
        String prefix = "RA";
        String line = "RAAAAAAAAAAA";
        Assertions.assertTrue(prefix.startsWith(line));
    }


}