package org.uma.daiwaScarlet.component;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uma.daiwaScarlet.context.RecordSpecItems;
import org.uma.daiwaScarlet.util.JvLinkStringUtil;
import org.uma.vodka.config.spec.RecordSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JvLinkModelMapper {

    /**
     * {@link org.uma.daiwaScarlet.configuration.JvLinkModelMapperConfiguration}
     */
    private final ModelMapper modelMapper;

    /**
     * BeanのMapオブジェクト
     * {@link org.uma.daiwaScarlet.configuration.JvLinkRecordColumnConfiguration}
     */
    private final Map<String, RecordSpecItems> recordSpecItemsMap;





    @Autowired
    public JvLinkModelMapper(ModelMapper modelMapper, Map<String, RecordSpecItems> recordSpecItemsMap) {
        this.modelMapper = modelMapper;
        this.recordSpecItemsMap = recordSpecItemsMap;
    }

    private RecordSpecItems findOne(RecordSpec recordSpec) {
        return recordSpecItemsMap.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(recordSpec.getCode()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public <T> T deserialize(String line, Class<T> clazz) {
        Map<String, Object> deSerialMap = new HashMap<>();
        final byte[] byteArrayLine = JvLinkStringUtil.stringToByte(line);

        //その場しのぎ
        RecordSpec recordSpec = RecordSpec.RA;
        findOne(recordSpec).getRecordItems().forEach(record -> {
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
