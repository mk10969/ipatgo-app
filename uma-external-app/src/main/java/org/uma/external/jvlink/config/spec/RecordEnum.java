package org.uma.external.jvlink.config.spec;


import org.uma.external.jvlink.config.DataEnum;

public interface RecordEnum<S, R extends Enum<R>> extends DataEnum<S, R> {

    /**
     * JV-Data文字列の長さ
     */
    int getLength();

}
