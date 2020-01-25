package org.uma.jvLink.client.config.condition;

import org.uma.jvLink.client.config.DataEnum;
import org.uma.jvLink.client.config.spec.RecordSpec;

public interface OpenCondition<T extends Enum<T> & DataEnum<String, T>> {

    T getDataSpec();

    RecordSpec getRecordType();

}
