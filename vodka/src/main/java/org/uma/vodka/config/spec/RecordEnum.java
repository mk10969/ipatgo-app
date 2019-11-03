package org.uma.vodka.config.spec;

import org.uma.vodka.common.constants.CodeEnum;

public interface RecordEnum<S, R extends Enum<R>> extends CodeEnum<S, R> {

    /**
     * JV-Data文字列の長さ
     */
    int getLength();

}
