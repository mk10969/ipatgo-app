package org.uma.daiwaScarlet.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.daiwaScarlet.context.RecordSpecItems;
import org.uma.daiwaScarlet.model.RaceDetailsModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.file.Files.readAllLines;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HelloMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier("RA")
    private RecordSpecItems recordSpecItems;


    private List<String> test1() throws IOException {
        Path path = Paths.get("/Users/m-kakiuchi/myApp/UmaApplication/daiwaScarlet/src/test/java/org/uma/daiwaScarlet/test/ra_data.txt");
        return readAllLines(path, Charset.forName("SHIFT-JIS"));
    }

    @Test
    void test() {
        // Inject OK
        System.out.println(modelMapper);
        System.out.println(recordSpecItems);
    }

    @Test
    void test_RAモデルマッパー_データは単一ファイル() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        test1().stream()
                .map(line -> mapper(line, RaceDetailsModel.class))
                .map(model -> {
                    try {
                        return objectMapper.writeValueAsString(model);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return model;
                })
                .forEach(System.out::println);

    }


    private <T> T mapper(String line, Class<T> clazz) {
        Map<String, Object> map = new HashMap<>();
        final byte[] byteArrayLine = StringUtils.stringToByte(line);

        recordSpecItems.getRecordItems().forEach(record -> {
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
                        for (Map.Entry<String, Integer> entry : StringUtils.jsonStringToMap(jsonString).entrySet()) {
                            String tmpString = StringUtils.byteToStringOnSlice(byteArrayLine, start, start + entry.getValue());
                            tmpMap.put(entry.getKey(), tmpString);
                            start = start + entry.getValue(); // こういうコードあまりよろしくない。
                        }
                        tmpList.add(tmpMap);
                    }
                    map.put(record.getColumn(), tmpList);
                }
                // 単純な繰り返し。
                else {
                    for (int i = 0; i < record.getRepeat(); i++) {
                        String tmpString = StringUtils.byteToStringOnSlice(byteArrayLine, start, start + record.getLength());
                        tmpList.add(tmpString);
                        start = start + record.getLength(); // こういうコードあまりよろしくない。
                    }
                    map.put(record.getColumn(), tmpList);
                }
            }
            // 繰り返しなし
            else {
                int end = record.getStart() + record.getLength(); // 次のメソッドの引数が長くなるから。
                String tmpString = StringUtils.byteToStringOnSlice(byteArrayLine, record.getStart(), end);
                map.put(record.getColumn(), tmpString);
            }
        });

        return modelMapper.map(map, clazz);
    }


    public static class StringUtils {
        private static final String characterCode = "x-SJIS_0213";
        private static final ObjectMapper objectMapper = new ObjectMapper();

        /**
         * 戻りの型が決まっているのでジェネリックは考えない。
         *
         * @param json
         * @return
         */
        public static Map<String, Integer> jsonStringToMap(String json) {
            Objects.requireNonNull(json);
            try {
                return objectMapper.readValue(json, new TypeReference<LinkedHashMap<String, Integer>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }

        public static byte[] stringToByte(String str) {
            Objects.requireNonNull(str);
            try {
                return str.getBytes(characterCode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                // Runtimeに変えること
                return null;
            }
        }

        public static String byteToStringOnSlice(byte[] array, int start, int end) {
            final byte[] slice = Arrays.copyOfRange(array, start, end);
            final ByteBuffer byteBuffer = ByteBuffer.wrap(slice);

            try {
                return Charset.forName(characterCode)
                        .newDecoder()
                        .decode(byteBuffer)
                        .toString();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
                // Runtimeに変えること
                throw new RuntimeException();
            }
        }
    }



    @Test
    void test_JsonStringうまくパースできるか() throws IOException {

        String str = "cornerPassageRankItems={\"corner\":1,\"aroundCount\":1,\"passageRank\":70}";
        String json = str.substring(str.indexOf("=") + 1);

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<LinkedHashMap<String, Integer>> reference = new TypeReference<LinkedHashMap<String, Integer>>() {
        };
        Map<String, Integer> map = objectMapper.readValue(json, reference);

        map.entrySet().forEach((k) -> {
            System.out.println(k.getKey());
            System.out.println();
        });

        System.out.println(map);
    }

}
