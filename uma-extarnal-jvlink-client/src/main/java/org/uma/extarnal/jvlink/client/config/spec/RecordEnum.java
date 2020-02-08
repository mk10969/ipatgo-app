package org.uma.extarnal.jvlink.client.config.spec;


import org.uma.extarnal.jvlink.client.config.DataEnum;

public interface RecordEnum<S, R extends Enum<R>> extends DataEnum<S, R> {

    /**
     * JV-Data文字列の長さ
     */
    int getLength();

}
