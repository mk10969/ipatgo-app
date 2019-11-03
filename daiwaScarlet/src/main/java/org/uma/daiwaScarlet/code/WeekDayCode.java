package org.uma.daiwaScarlet.code;

import org.uma.vodka.common.constants.CodeEnum;

import java.util.Objects;

/**
 * 2002.曜日コード
 */
public enum WeekDayCode implements CodeEnum<String, WeekDayCode> {

    /**
     * デフォルト
     */
    DEFAULT("0", "", ""),
    SATURDAY("1", "土曜日", "土"),
    SUNDAY("2", "日曜日", "日"),
    HOLIDAY("3", "祝日", "祝"),
    MONDAY("4", "月曜日", "月"),
    TUESDAY("5", "火曜日", "火"),
    WEDNESDAY("6", "水曜日", "水"),
    THURSDAY("7", "木曜日", "木"),
    FRIDAY("8", "金曜日", "金"),

    ;

    private String code;
    private String weekDay;
    private String weekDayShort;

    WeekDayCode(String code, String weekDay, String weekDayShort) {
        this.code = code;
        this.weekDay = weekDay;
        this.weekDayShort = weekDayShort;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getWeekDay() {
        return this.weekDay;
    }

    public String getWeekDayShort() {
        return this.weekDayShort;
    }

    public static WeekDayCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, WeekDayCode.class);
    }


}
