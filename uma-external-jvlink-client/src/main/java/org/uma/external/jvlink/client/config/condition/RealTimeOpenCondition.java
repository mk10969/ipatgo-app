package org.uma.external.jvlink.client.config.condition;

import org.uma.external.jvlink.client.config.spec.RealTimeDataSpec;
import org.uma.external.jvlink.client.config.spec.RecordSpec;

public class RealTimeOpenCondition implements OpenCondition<RealTimeDataSpec> {

    private final RealTimeDataSpec dataSpec;
    private final RecordSpec recordType;

    public RealTimeOpenCondition(RealTimeDataSpec dataSpec, RecordSpec recordType) {
        this.dataSpec = dataSpec;
        this.recordType = recordType;
    }

    @Override
    public RealTimeDataSpec getDataSpec() {
        return this.dataSpec;
    }

    @Override
    public RecordSpec getRecordType() {
        return this.recordType;
    }

    @Override
    public String toString() {
        return "RealTimeOpenCondition{" +
                "dataSpec=" + dataSpec +
                ", recordType=" + recordType +
                '}';
    }

}
