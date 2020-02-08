package org.uma.extarnal.jvlink.client.config.condition;

import org.uma.extarnal.jvlink.client.config.DataEnum;
import org.uma.extarnal.jvlink.client.config.spec.RecordSpec;

public interface OpenCondition<T extends Enum<T> & DataEnum<String, T>> {

    T getDataSpec();

    RecordSpec getRecordType();

}
