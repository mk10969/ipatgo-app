package org.uma.daiwaScarlet.context;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class RecordSpecItems {

    private List<RecordItem> recordItems = new ArrayList<>();

    @Data
    public static class RecordItem {

        private String column;

        private int start;

        private int Length;

        private int repeat;

    }
}

