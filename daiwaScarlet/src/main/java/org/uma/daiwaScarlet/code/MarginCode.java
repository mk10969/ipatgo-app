package org.uma.daiwaScarlet.code;


import org.apache.commons.lang3.StringUtils;
import org.uma.vodka.common.constants.CodeEnum;

import java.util.Objects;

/**
 * 2102.着差コード
 */
public enum MarginCode implements CodeEnum<String, MarginCode> {

    /**
     * デフォルト
     */
    DEFAULT("___", ""),
    BY_HALF("_12", "1/2馬身"),
    BY_3QTR("_34", "3/4馬身"),
    BY_ONE("1__", "１馬身"),
    BY_ONE_HALF("112", "１1/2馬身"),
    BY_ONE_QTR("114", "１1/4馬身"),
    BY_ONE_3QTR("134", "１3/4馬身"),
    BY_TWO("2__", "２馬身"),
    BY_TWO_HALF("212", "２1/2馬身"),
    BY_THREE("3__", "３馬身"),
    BY_THREE_HALF("312", "３1/2馬身"),
    BY_FOUR("4__", "４馬身"),
    BY_FIVE("5__", "５馬身"),
    BY_SIX("6__", "６馬身"),
    BY_SEVEN("7__", "７馬身"),
    BY_EIGHT("8__", "８馬身"),
    BY_NINE("9__", "９馬身"),
    BY_HEAD("A__", "アタマ"),
    BY_DEADHEAT("D__", "同着"),
    BY_NOSE("H__", "ハナ"),
    BY_NECK("K__", "クビ"),
    BY_DISTANCE("T__", "大差"),
    BY_TEN("Z__", "１０馬身"),
    BY_QTR("_14", "1/4馬身"),
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
        if (StringUtils.isBlank(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, MarginCode.class);
    }

}
