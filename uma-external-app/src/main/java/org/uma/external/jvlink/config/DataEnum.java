package org.uma.external.jvlink.config;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface DataEnum<T, E extends Enum<E>> {

    T getCode();

    /**
     * Enum型にキャストする。
     * シンプルに活用しているのが、toEnum()メソッド。
     * サブクラスの型を扱う際に便利 =>「再帰的ジェネリクス」
     */
    @SuppressWarnings("unchecked")
    default E toEnum() {
        return (E) this;
    }

    /**
     * Enumを表示順にソートしたリストを返却する。
     */
    static <T, E extends Enum<E>> List<E> getOrderedList(Class<? extends DataEnum<T, E>> enumClazz) {
        return Stream.of(enumClazz.getEnumConstants())
                .sorted()
                .map(DataEnum::toEnum)
                .collect(Collectors.toList());
    }

    /**
     * Codeから逆引きして、Enumを返却する。
     */
    static <T, E extends Enum<E>> E reversibleFindOne(T code, Class<? extends DataEnum<T, E>> enumClazz) {
        return Stream.of(enumClazz.getEnumConstants())
                .filter(enums -> Objects.equals(enums.getCode(), code))
                .findFirst()
                .map(DataEnum::toEnum)
                .orElseThrow(() -> new IllegalArgumentException(
                        "[引数のコード :" + code + "] " + enumClazz + " に引数のコードが、存在しません。"
                ));
    }
}
