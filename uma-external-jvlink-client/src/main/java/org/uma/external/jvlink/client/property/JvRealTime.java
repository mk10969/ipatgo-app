package org.uma.external.jvlink.client.property;

import org.uma.external.jvlink.client.config.condition.RealTimeOpenCondition;
import org.uma.external.jvlink.client.config.spec.RealTimeDataSpec;
import org.uma.external.jvlink.client.config.spec.RecordSpec;

public enum JvRealTime {

    ///// リアルタイム系データ /////

    /**
     * 出走馬名表 レース詳細
     */
    OB15_RA(new RealTimeOpenCondition(RealTimeDataSpec.OB15, RecordSpec.RA)),

    /**
     * 出走馬名表 馬毎レース情報
     */
    OB15_SE(new RealTimeOpenCondition(RealTimeDataSpec.OB15, RecordSpec.SE)),

    /**
     * 出走馬名表 払戻
     */
    OB15_HR(new RealTimeOpenCondition(RealTimeDataSpec.OB15, RecordSpec.HR)),

    /**
     * 速報オッズ（単複枠）
     */
    OB31_O1(new RealTimeOpenCondition(RealTimeDataSpec.OB31, RecordSpec.O1)),

    /**
     * 速報オッズ（馬連）
     */
    OB32_O2(new RealTimeOpenCondition(RealTimeDataSpec.OB32, RecordSpec.O2)),

    /**
     * 速報オッズ（ワイド）
     */
    OB33_O3(new RealTimeOpenCondition(RealTimeDataSpec.OB33, RecordSpec.O3)),

    /**
     * 速報オッズ（馬単）
     */
    OB34_O4(new RealTimeOpenCondition(RealTimeDataSpec.OB34, RecordSpec.O4)),

    /**
     * 速報オッズ（３連複）
     */
    OB35_O5(new RealTimeOpenCondition(RealTimeDataSpec.OB35, RecordSpec.O5)),

    /**
     * 速報オッズ（３連単）
     */
    OB36_O6(new RealTimeOpenCondition(RealTimeDataSpec.OB36, RecordSpec.O6)),

    /**
     * 速報票数（３連単以外）
     */
    OB20_H1(new RealTimeOpenCondition(RealTimeDataSpec.OB20, RecordSpec.H1)),

    /**
     * 速報票数（３連単）
     */
    OB20_H6(new RealTimeOpenCondition(RealTimeDataSpec.OB20, RecordSpec.H6)),

    /**
     * 馬体重
     */
    OB11_WH(new RealTimeOpenCondition(RealTimeDataSpec.OB11, RecordSpec.WH)),

    /**
     * 時系列オッズ（単複枠）
     */
    OB41_O1(new RealTimeOpenCondition(RealTimeDataSpec.OB41, RecordSpec.O1)),

    /**
     * 時系列オッズ（馬連）
     */
    OB42_O2(new RealTimeOpenCondition(RealTimeDataSpec.OB42, RecordSpec.O2)),

    /**
     * WIN5
     */
    OB51_WF(new RealTimeOpenCondition(RealTimeDataSpec.OB51, RecordSpec.WF)),


    ///// イベント通知系データ /////

    /**
     * 天候馬場状態
     */
    OB16_WE(new RealTimeOpenCondition(RealTimeDataSpec.OB16, RecordSpec.WE)),

    /**
     * 出走取り消し・競争除外
     */
    OB16_AV(new RealTimeOpenCondition(RealTimeDataSpec.OB16, RecordSpec.AV)),

    /**
     * 騎手変更
     */
    OB16_JC(new RealTimeOpenCondition(RealTimeDataSpec.OB16, RecordSpec.JC)),

    /**
     * 発走時刻変更
     */
    OB16_TC(new RealTimeOpenCondition(RealTimeDataSpec.OB16, RecordSpec.TC)),

    /**
     * コース変更
     */
    OB16_CC(new RealTimeOpenCondition(RealTimeDataSpec.OB16, RecordSpec.CC)),

    ;


    private final RealTimeOpenCondition realTimeOpenCondition;

    JvRealTime(RealTimeOpenCondition realTimeOpenCondition) {
        this.realTimeOpenCondition = realTimeOpenCondition;
    }

    public RealTimeOpenCondition get() {
        return this.realTimeOpenCondition;
    }


}
