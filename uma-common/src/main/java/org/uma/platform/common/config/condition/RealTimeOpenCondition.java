package org.uma.platform.common.config.condition;

import org.uma.platform.common.config.spec.RealTimeDataSpec;
import org.uma.platform.common.config.spec.RecordSpec;

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


}
