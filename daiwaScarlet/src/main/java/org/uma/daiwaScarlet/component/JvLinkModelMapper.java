package org.uma.daiwaScarlet.component;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uma.daiwaScarlet.configuration.JvLinkRecordSpecConfiguration;
import org.uma.daiwaScarlet.context.RecordSpecItems;
import org.uma.daiwaScarlet.util.JvLinkStringUtil;
import org.uma.vodka.config.spec.RecordSpec;

import java.util.*;

@Component
public class JvLinkModelMapper {

    private final ModelMapper modelMapper;

    private final EnumMap<RecordSpec, Class<?>> recordSpecClass;

    /**
     * 同一型のBeanをMap化
     * {@link JvLinkRecordSpecConfiguration}
     */
    private final Map<String, RecordSpecItems> recordSpecItems;


    @Autowired
    public JvLinkModelMapper(ModelMapper modelMapper, EnumMap<RecordSpec, Class<?>> recordSpecClass, Map<String, RecordSpecItems> recordSpecItems) {
        this.modelMapper = modelMapper;
        this.recordSpecClass = recordSpecClass;
        this.recordSpecItems = recordSpecItems;
    }

    /**
     * clazzから、RecordSpecを抽出し、flatMapをつかって、RecordSpecから、RecordSpecItemsを抽出する。
     * @param clazz
     * @return RecordSpecItems
     */
    private RecordSpecItems findOne(Class<?> clazz) {
        return recordSpecClass.entrySet()
                .stream()
                .filter(i -> Objects.equals(i.getValue(), clazz))
                .map(Map.Entry::getKey)
                .flatMap(i -> recordSpecItems.entrySet()
                        .stream()
                        .filter(e -> Objects.equals(e.getKey(), i.getCode()))
                        .map(Map.Entry::getValue))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     *
     * @param line    JvLinkから得られるデータ1行
     * @param clazz   Modelクラス
     * @param <T>     Modelクラスの型
     * @return        deserialized model クラス
     */
    public <T> T deserialize(String line, Class<T> clazz) {
        Map<String, Object> deSerialMap = new HashMap<>();
        final byte[] byteArrayLine = JvLinkStringUtil.stringToByte(line);

        findOne(clazz).getRecordItems().forEach(record -> {
            // 繰り返しあり。
            if (record.getRepeat() != 0) {
                int start = record.getStart();
                List<Object> tmpList = new ArrayList<>();

                // オブジェクトを繰り返す。
                if (record.getColumn().contains("=")) {
                    // 意図的にjson文字列を設定しておく。
                    String jsonString = record.getColumn().substring(record.getColumn().indexOf("=") + 1);

                    Map<String, Object> tmpMap = new HashMap<>();
                    for (int i = 0; i < record.getRepeat(); i++) {
                        for (Map.Entry<String, Integer> entry : JvLinkStringUtil.jsonStringToMap(jsonString).entrySet()) {
                            String tmpString = JvLinkStringUtil.byteToStringOnSlice(byteArrayLine, start, start + entry.getValue());
                            tmpMap.put(entry.getKey(), tmpString);
                            start = start + entry.getValue(); // こういうコードあまりよろしくない。
                        }
                        tmpList.add(tmpMap);
                    }
                    deSerialMap.put(record.getColumn(), tmpList);
                }
                // 単純な繰り返し。
                else {
                    for (int i = 0; i < record.getRepeat(); i++) {
                        String tmpString = JvLinkStringUtil.byteToStringOnSlice(byteArrayLine, start, start + record.getLength());
                        tmpList.add(tmpString);
                        start = start + record.getLength(); // こういうコードあまりよろしくない。
                    }
                    deSerialMap.put(record.getColumn(), tmpList);
                }
            }
            // 繰り返しなし
            else {
                int end = record.getStart() + record.getLength(); // 次のメソッドの引数が長くなるから。
                String tmpString = JvLinkStringUtil.byteToStringOnSlice(byteArrayLine, record.getStart(), end);
                deSerialMap.put(record.getColumn(), tmpString);
            }
        });

        return modelMapper.map(deSerialMap, clazz);
    }


}
