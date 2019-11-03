package org.uma.daiwaScarlet.code;


import org.uma.vodka.common.constants.CodeEnum;

import java.util.Objects;

/**
 * 2302.騎乗資格コード
 */
public enum JockeyLicenseCode implements CodeEnum<Integer, JockeyLicenseCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    BOTH(1, "平地・障害"),
    FLAT(2, "平地"),
    JUMP(3, "障害"),
    ;

    private Integer code;
    private String codeName;

    JockeyLicenseCode(Integer code, String codeName) {
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

    public static JockeyLicenseCode of(Integer code) {
        Objects.requireNonNull(code);
        if (0 == code) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, JockeyLicenseCode.class);
    }


}
