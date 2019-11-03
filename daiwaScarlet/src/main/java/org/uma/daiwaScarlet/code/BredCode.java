package org.uma.daiwaScarlet.code;


import org.uma.vodka.common.constants.CodeEnum;

import java.util.Objects;

/**
 * 2201.品種コード
 */
public enum BredCode implements CodeEnum<Integer, BredCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    BRED1(1, "サラブレッド"),
    BRED2(2, "サラブレッド系種"),
    BRED3(3, "準サラブレッド"),
    BRED4(4, "軽半血種"),
    BRED5(5, "アングロアラブ"),
    BRED6(6, "アラブ系種"),
    BRED7(7, "アラブ"),
    BRED8(8, "中半血種"),

    ;

    private Integer code;
    private String codeName;

    BredCode(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public static BredCode of(Integer code) {
        Objects.requireNonNull(code);
        if (0 == code) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, BredCode.class);
    }

}
