package org.uma.platform.feed.application.test;

import org.junit.jupiter.api.Test;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.configuration.JvLinkRecordSpecConfiguration.RecordSpecItems;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class TestEnumMap {

    @Test
    void test_EnumMap使ってみる() {
        // 便利だ！
        EnumMap<DayOfWeek, String> activityMap = new EnumMap<>(DayOfWeek.class);
        activityMap.put(DayOfWeek.MONDAY, "月曜");
        activityMap.put(DayOfWeek.TUESDAY, "火曜");
        activityMap.put(DayOfWeek.WEDNESDAY, "水曜");
        activityMap.put(DayOfWeek.THURSDAY, "木曜");
        activityMap.put(DayOfWeek.FRIDAY, "金曜");

        activityMap.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println(activityMap.get(DayOfWeek.FRIDAY));
    }

    @Test
    void test_RecordSpecをEnumMap化してみる() {

        Map<Class<?>, RecordSpecItems> map = new HashMap<>();
        map.put(RacingDetails.class, ra());

        EnumMap<RecordSpec, Map<Class<?>, RecordSpecItems>> activityMap = new EnumMap<>(RecordSpec.class);
        activityMap.put(RecordSpec.RA, map);

        System.out.println(activityMap);

    }

    private RecordSpecItems ra() {
        return new RecordSpecItems();

    }


}