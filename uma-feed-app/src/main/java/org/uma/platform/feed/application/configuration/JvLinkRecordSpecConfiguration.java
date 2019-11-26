package org.uma.platform.feed.application.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;


@Configuration
@PropertySource(value = "classpath:JvLinkRecord.properties")
public class JvLinkRecordSpecConfiguration {

    /**
     * @return レース詳細
     */
    @Bean(name = "RA")
    @ConfigurationProperties(prefix = "RA")
    RecordSpecItems configRA() {
        return new RecordSpecItems();
    }

    /**
     * @return 出走馬詳細
     */
    @Bean(name = "SE")
    @ConfigurationProperties(prefix = "SE")
    RecordSpecItems configSE() {
        return new RecordSpecItems();
    }

    /**
     * @return レース払戻
     */
    @Bean(name = "HR")
    @ConfigurationProperties(prefix = "HR")
    RecordSpecItems configHR() {
        return new RecordSpecItems();
    }

    /**
     * @return レース票数（単勝・複勝・枠連・馬連・ワイド・馬単・三連複）
     */
    @Bean(name = "H1")
    @ConfigurationProperties(prefix = "H1")
    RecordSpecItems configH1() {
        return new RecordSpecItems();
    }

    /**
     * @return 競走馬除外情報
     */
    @Bean(name = "JG")
    @ConfigurationProperties(prefix = "JG")
    RecordSpecItems configJG() {
        return new RecordSpecItems();
    }

    /**
     * @return 産駒マスタ
     */
    @Bean(name = "SK")
    @ConfigurationProperties(prefix = "SK")
    RecordSpecItems configSK() {
        return new RecordSpecItems();
    }

    /**
     * @return 系統情報
     */
    @Bean(name = "BT")
    @ConfigurationProperties(prefix = "BT")
    RecordSpecItems configBT() {
        return new RecordSpecItems();
    }

    /**
     * @return 繁殖馬マスタ
     */
    @Bean(name = "HN")
    @ConfigurationProperties(prefix = "HN")
    RecordSpecItems configHN() {
        return new RecordSpecItems();
    }

    /**
     * @return コース情報
     */
    @Bean(name = "CS")
    @ConfigurationProperties(prefix = "CS")
    RecordSpecItems configCS() {
        return new RecordSpecItems();
    }


    /**
     * @return 競走馬マスタ
     */
    @Bean(name = "UM")
    @ConfigurationProperties(prefix = "UM")
    RecordSpecItems configUM() {
        return new RecordSpecItems();
    }

    /**
     * @return 騎手マスタ
     */
    @Bean(name = "KS")
    @ConfigurationProperties(prefix = "KS")
    RecordSpecItems configKS() {
        return new RecordSpecItems();
    }

    /**
     * @return 調教師マスタ
     */
    @Bean(name = "CH")
    @ConfigurationProperties(prefix = "CH")
    RecordSpecItems configCH() {
        return new RecordSpecItems();
    }

    /**
     * @return 生産者マスタ
     */
    @Bean(name = "BR")
    @ConfigurationProperties(prefix = "BR")
    RecordSpecItems configBR() {
        return new RecordSpecItems();
    }

    /**
     * @return 馬主マスタ
     */
    @Bean(name = "BN")
    @ConfigurationProperties(prefix = "BN")
    RecordSpecItems configBN() {
        return new RecordSpecItems();
    }


    ///////// リアルタイム //////////

    /**
     * @return 馬体重情報
     */
    @Bean(name = "WH")
    @ConfigurationProperties(prefix = "WH")
    RecordSpecItems configWH() {
        return new RecordSpecItems();
    }

    /**
     * @return 天候馬場状態
     */
    @Bean(name = "WE")
    @ConfigurationProperties(prefix = "WE")
    RecordSpecItems configWE() {
        return new RecordSpecItems();
    }

    /**
     * @return 出走取消競争除外
     */
    @Bean(name = "AV")
    @ConfigurationProperties(prefix = "AV")
    RecordSpecItems configAV() {
        return new RecordSpecItems();
    }

    /**
     * @return 騎手変更
     */
    @Bean(name = "JC")
    @ConfigurationProperties(prefix = "JC")
    RecordSpecItems configJC() {
        return new RecordSpecItems();
    }

    /**
     * @return 発走時刻変更
     */
    @Bean(name = "TC")
    @ConfigurationProperties(prefix = "TC")
    RecordSpecItems configTC() {
        return new RecordSpecItems();
    }


    /**
     * シリアライズデータを、
     * Javaオブジェクト化するためのフォーマットクラス
     * <p>
     * カラム名、開始位置、データ長さ、繰り返しフラグ
     */
    @Getter
    @ToString
    public static class RecordSpecItems {

        private List<RecordItem> recordItems = new ArrayList<>();

        @Data
        public static class RecordItem {

            private String column;

            private int start;

            private int Length;

            private int repeat;

        }
    }

}
