package org.uma.platform.common.config.spec;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;

public enum StoredDataSpec implements DataEnum<String, StoredDataSpec> {

    TOKU("TOKU", "特別登録馬情報"),
    RACE("RACE", "レース情報"),
    DIFF("DIFF", "蓄積系ソフト用 蓄積情報"),
    BLOD("BLOD", "蓄積系ソフト用 血統情報"),
    MING("MING", "蓄積系ソフト用 マイニング情報"),
    SNAP("SNAP", "出走時点情報"),
    SLOP("SLOP", "坂路調教情報"),
    YSCH("YSCH", "開催スケジュール"),
    HOSE("HOSE", "競走馬市場取引価格情報"),
    HOYU("HOYU", "馬名の意味由来情報"),
    COMM("COMM", "各種解説情報"),
    TCOV("TCOV", "非蓄積系ソフト用 補てん情報(特別登録馬情報補てん)"),
    RCOV("RCOV", "非蓄積系ソフト用 補てん情報(レース情報補てん)"),
    ;

    private String code;
    private String name;

    StoredDataSpec(String code, String name) {
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

    public static StoredDataSpec of(String dataSpec) {
        Objects.requireNonNull(dataSpec);
        return CodeEnum.reversibleFindOne(dataSpec, StoredDataSpec.class);
    }

}
