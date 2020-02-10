package org.uma.external.jvlink.client.config.condition;

import org.uma.external.jvlink.client.config.DataEnum;
import org.uma.external.jvlink.client.config.spec.RecordSpec;

public interface OpenCondition<T extends Enum<T> & DataEnum<String, T>> {

    T getDataSpec();

    RecordSpec getRecordType();

}
