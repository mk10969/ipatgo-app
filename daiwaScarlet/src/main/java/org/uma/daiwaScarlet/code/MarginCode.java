package org.uma.daiwaScarlet.code;


import org.uma.vodka.common.constants.CodeEnum;

import java.util.Objects;

/**
 * 2102.着差コード
 */
public enum MarginCode implements CodeEnum<String, MarginCode> {

    /**
     * デフォルト
     */
    DEFAULT("   ", ""),
    BY_HALF(" 12", "1/2馬身"),
    BY_3QTR(" 34", "3/4馬身"),
    BY_ONE("1  ", "１馬身"),
    BY_ONE_HALF("112", "１1/2馬身"),
    BY_ONE_QTR("114", "１1/4馬身"),
    BY_ONE_3QTR("134", "１3/4馬身"),
    BY_TWO("2  ", "２馬身"),
    BY_TWO_HALF("212", "２1/2馬身"),
    BY_THREE("3  ", "３馬身"),
    BY_THREE_HALF("312", "３1/2馬身"),
    BY_FOUR("4  ", "４馬身"),
    BY_FIVE("5  ", "５馬身"),
    BY_SIX("6  ", "６馬身"),
    BY_SEVEN("7  ", "７馬身"),
    BY_EIGHT("8  ", "８馬身"),
    BY_NINE("9  ", "９馬身"),
    BY_HEAD("A  ", "アタマ"),
    BY_DEADHEAT("D  ", "同着"),
    BY_NOSE("H  ", "ハナ"),
    BY_NECK("K  ", "クビ"),
    BY_DISTANCE("T  ", "大差"),
    BY_TEN("Z  ", "１０馬身"),
    BY_QTR(" 14", "1/4馬身"),
    BY_TWO_QTR("214", "２1/4馬身"),
    BY_SEVEN_3QTR("734", "７3/4馬身"),
    ;

    private String code;
    private String codeName;

    MarginCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public static MarginCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, MarginCode.class);
    }

}
