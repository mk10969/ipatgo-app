package org.uma.daiwaScarlet.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.uma.daiwaScarlet.context.RecordSpecItems;


@Configuration
@PropertySource(value = "classpath:JvLink.properties")
public class JvLinkRecordColumnConfiguration {

    /**
     * @return レース詳細
     */
    @Bean(name = "RA")
    @ConfigurationProperties(prefix = "ra")
    RecordSpecItems configRA() {
        return new RecordSpecItems();
    }

    /**
     * @return 出走馬詳細
     */
    @Bean(name = "SE")
    @ConfigurationProperties(prefix = "se")
    RecordSpecItems configSE() {
        return new RecordSpecItems();
    }

    /**
     * @return レース払戻
     */
    @Bean(name = "HR")
    @ConfigurationProperties(prefix = "hr")
    RecordSpecItems configHR() {
        return new RecordSpecItems();
    }

    /**
     * @return 産駒血統マスタ
     */
    @Bean(name = "SK")
    @ConfigurationProperties(prefix = "sk")
    RecordSpecItems configSK() {
        return new RecordSpecItems();
    }

    /**
     * @return 系統情報
     */
    @Bean(name = "BT")
    @ConfigurationProperties(prefix = "bt")
    RecordSpecItems configBT() {
        return new RecordSpecItems();
    }

    /**
     * @return 繁殖馬マスタ
     */
    @Bean(name = "HN")
    @ConfigurationProperties(prefix = "hn")
    RecordSpecItems configHN() {
        return new RecordSpecItems();
    }

    /**
     * @return コース情報
     */
    @Bean(name = "CS")
    @ConfigurationProperties(prefix = "cs")
    RecordSpecItems configCS() {
        return new RecordSpecItems();
    }


    /**
     * @return 競走馬マスタ
     */
    @Bean(name = "UM")
    @ConfigurationProperties(prefix = "um")
    RecordSpecItems configUM() {
        return new RecordSpecItems();
    }

    /**
     * @return 騎手マスタ
     */
    @Bean(name = "KS")
    @ConfigurationProperties(prefix = "ks")
    RecordSpecItems configKS() {
        return new RecordSpecItems();
    }

    /**
     * @return 調教師マスタ
     */
    @Bean(name = "CH")
    @ConfigurationProperties(prefix = "ch")
    RecordSpecItems configCH() {
        return new RecordSpecItems();
    }

    /**
     * @return 生産者マスタ
     */
    @Bean(name = "BR")
    @ConfigurationProperties(prefix = "br")
    RecordSpecItems configBR() {
        return new RecordSpecItems();
    }

    /**
     * @return 馬主マスタ
     */
    @Bean(name = "BN")
    @ConfigurationProperties(prefix = "bn")
    RecordSpecItems configBN() {
        return new RecordSpecItems();
    }


}
