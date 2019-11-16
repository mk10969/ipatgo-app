package org.uma.platform.common.config;

import org.uma.platform.common.utils.constants.CodeEnum;

public enum Option implements CodeEnum<Integer, Option> {

    STANDARD(1, "通常データ"),

    THIS_WEEK(2, "今週データ"),

    SETUP_WITH_DIALOG(3, "ダイアログありセットアップ"),

    SETUP_WITHOUT_DIALOG(4, "ダイアログなしセットアップ"),
    ;

    private final Integer code;
    private final String message;

    private Option(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.message;
    }

}
