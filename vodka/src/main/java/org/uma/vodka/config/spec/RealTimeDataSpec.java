package org.uma.vodka.config.spec;

import org.uma.vodka.common.constants.CodeEnum;

import java.util.Objects;

public enum RealTimeDataSpec implements DataEnum<String, RealTimeDataSpec> {

    OB12("0B12","速報レース情報(成績確定後)"),
    OB15("0B15","速報レース情報(出走場名表)"),
    OB30("0B30","速報オッズ(全賭式)"),
    OB31("0B31","速報オッズ(単複枠)"),
    OB32("0B32","速報オッズ(馬連)"),
    OB33("0B33","速報オッズ(ワイド)"),
    OB34("0B34","速報オッズ(馬単)"),
    OB35("0B35","速報オッズ(3連複)"),
    OB36("0B36","速報オッズ(3連単)"),
    OB20("0B20","速報票数(全賭式)"),
    OB11("0B11","速報馬体重"),
    OB14("0B14","速報開催情報(一括)"),
    OB16("0B16","速報開催情報(指定)"),
    OB13("0B13","速報タイム型データマイニング予想"),
    OB17("0B17","速報対戦型データマイニング予想"),
    OB41("0B41","時系列オッズ(単複枠)"),
    OB42("0B42","時系列オッズ(馬連)"),
    OB51("0B51","速報重勝式(WIN5)"),
    ;

    private String code;
    private String name;

    RealTimeDataSpec(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public RealTimeDataSpec findByDataSpec(String dataSpec) {
        Objects.requireNonNull(dataSpec);
        return CodeEnum.reversibleFindOne(dataSpec, RealTimeDataSpec.class);
    }

}
