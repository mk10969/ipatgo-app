package org.uma.jvLink.core.config.spec;

import org.uma.jvLink.core.config.DataEnum;

import java.util.Objects;

public enum RecordSpec implements RecordEnum<String, RecordSpec> {

    TK("TK", "特別登録馬", 21657),
    RA("RA", "レース詳細", 1272),
    SE("SE", "馬毎レース情報", 555),
    HR("HR", "払戻", 719),
    H1("H1", "票数(3連単以外)", 28955),
    H6("H6", "票数(3連単)", 102890),
    O1("O1", "オッズ(単勝, 複勝, 枠連)", 962),
    O2("O2", "オッズ(馬連)", 2042),
    O3("O3", "オッズ(ワイド)", 2654),
    O4("O4", "オッズ(馬単)", 4031),
    O5("O5", "オッズ(3連複)", 12293),
    O6("O6", "オッズ(3連単)", 83285),
    UM("UM", "競走馬マスタ", 1577),
    KS("KS", "騎手マスタ", 4173),
    CH("CH", "調教師マスタ", 3862),
    BR("BR", "生産者マスタ", 537),
    BN("BN", "馬主マスタ", 477),
    HN("HN", "繁殖馬マスタ", 245),
    SK("SK", "産駒マスタ", 178),
    CK("CK", "出走別着度数", 6864),
    RC("RC", "レコードマスタ", 501),
    HC("HC", "坂路調教", 60),
    HS("HS", "競走馬市場取引価格", 196),
    HY("HY", "馬名の意味由来情報", 123),
    YS("YS", "開催スケジュール", 382),
    BT("BT", "系統情報", 6887),
    CS("CS", "コース情報", 6829),
    DM("DM", "タイム型データマイニング予想", 303),
    TM("TM", "対戦型データマイニング予想", 141),
    WF("WF", "重勝式(WIN5)", 7215),
    JG("JG", "競走馬除外情報", 80),
    WH("WH", "馬体重", 847),
    WE("WE", "天候馬場状態", 42),
    AV("AV", "出走取消競争除外", 78),
    JC("JC", "騎手変更", 161),
    TC("TC", "発走時刻変更", 45),
    CC("CC", "コース変更", 50),
    ;

    private String code;
    private String name;
    private int length;

    RecordSpec(String code, String name, int length) {
        this.code = code;
        this.name = name;
        this.length = length;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    public static RecordSpec of(String recordSpec) {
        Objects.requireNonNull(recordSpec);
        return DataEnum.reversibleFindOne(recordSpec, RecordSpec.class);
    }


}
