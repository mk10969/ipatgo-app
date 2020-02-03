package org.uma.jvLink.client.property;

import org.uma.jvLink.client.config.condition.RealTimeOpenCondition;

import static org.uma.jvLink.client.config.spec.RealTimeDataSpec.*;
import static org.uma.jvLink.client.config.spec.RecordSpec.*;

public enum JvRealTime {

    ///// リアルタイム系データ /////

    /**
     * 出走馬名表 レース詳細
     */
    OB15_RA(new RealTimeOpenCondition(OB15, RA)),

    /**
     * 出走馬名表 馬毎レース情報
     */
    OB15_SE(new RealTimeOpenCondition(OB15, SE)),

    /**
     * 出走馬名表 払戻
     */
    OB15_HR(new RealTimeOpenCondition(OB15, HR)),

    /**
     * 速報オッズ（単複枠）
     */
    OB31_O1(new RealTimeOpenCondition(OB31, O1)),

    /**
     * 速報オッズ（馬連）
     */
    OB32_O2(new RealTimeOpenCondition(OB32, O2)),

    /**
     * 速報オッズ（ワイド）
     */
    OB33_O3(new RealTimeOpenCondition(OB33, O3)),

    /**
     * 速報オッズ（馬単）
     */
    OB34_O4(new RealTimeOpenCondition(OB34, O4)),

    /**
     * 速報オッズ（３連複）
     */
    OB35_O5(new RealTimeOpenCondition(OB35, O5)),

    /**
     * 速報オッズ（３連単）
     */
    OB36_O6(new RealTimeOpenCondition(OB36, O6)),

    /**
     * 速報票数（３連単以外）
     */
    OB20_H1(new RealTimeOpenCondition(OB20, H1)),

    /**
     * 速報票数（３連単）
     */
    OB20_H6(new RealTimeOpenCondition(OB20, H6)),

    /**
     * 馬体重
     */
    OB11_WH(new RealTimeOpenCondition(OB11, WH)),

    /**
     * 時系列オッズ（単複枠）
     */
    OB41_O1(new RealTimeOpenCondition(OB41, O1)),

    /**
     * 時系列オッズ（馬連）
     */
    OB42_O2(new RealTimeOpenCondition(OB42, O2)),

    /**
     * WIN5
     */
    OB51_WF(new RealTimeOpenCondition(OB51, WF)),


    ///// イベント通知系データ /////

    /**
     * 天候馬場状態
     */
    OB16_WE(new RealTimeOpenCondition(OB16, WE)),

    /**
     * 出走取り消し・競争除外
     */
    OB16_AV(new RealTimeOpenCondition(OB16, AV)),

    /**
     * 騎手変更
     */
    OB16_JC(new RealTimeOpenCondition(OB16, JC)),

    /**
     * 発走時刻変更
     */
    OB16_TC(new RealTimeOpenCondition(OB16, TC)),

    /**
     * コース変更
     */
    OB16_CC(new RealTimeOpenCondition(OB16, CC)),

    ;


    private final RealTimeOpenCondition realTimeOpenCondition;

    JvRealTime(RealTimeOpenCondition realTimeOpenCondition) {
        this.realTimeOpenCondition = realTimeOpenCondition;
    }

    public RealTimeOpenCondition get() {
        return this.realTimeOpenCondition;
    }


}
