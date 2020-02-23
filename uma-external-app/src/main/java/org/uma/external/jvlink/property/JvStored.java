package org.uma.external.jvlink.property;

import org.uma.external.jvlink.config.condition.StoredOpenCondition;
import org.uma.external.jvlink.config.spec.RecordSpec;
import org.uma.external.jvlink.config.spec.StoredDataSpec;

public enum JvStored {

    ///// 蓄積系データ /////

    /**
     * レース詳細
     */
    RACE_RA(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA)),

    /**
     * 出走馬詳細
     */
    RACE_SE(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.SE)),

    /**
     * レース払戻
     */
    RACE_HR(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.HR)),

    /**
     * 確定票数（3連単以外）
     */
    RACE_H1(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.H1)),

    /**
     * 確定票数（3連単）
     */
    RACE_H6(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.H6)),

    /**
     * 確定オッズ（単複枠）
     */
    RACE_O1(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.O1)),

    /**
     * 確定オッズ（馬連）
     */
    RACE_O2(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.O2)),

    /**
     * 確定オッズ（ワイド）
     */
    RACE_O3(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.O3)),

    /**
     * 確定オッズ（馬単）
     */
    RACE_O4(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.O4)),

    /**
     * 確定オッズ（３連複）
     */
    RACE_O5(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.O5)),

    /**
     * 確定オッズ（３連単）
     */
    RACE_O6(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.O6)),

    /**
     * WIN5
     */
    RACE_WF(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.WF)),

    /**
     * 競走馬除外情報
     */
    RACE_JG(new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.JG)),

    /**
     * 競走馬のマスタの差分
     */
    DIFF_UM(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.UM)),

    /**
     * 騎手マスタの差分
     */
    DIFF_KS(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.KS)),

    /**
     * 調教師マスタの差分
     */
    DIFF_CH(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.CH)),

    /**
     * 生産者マスタの差分
     */
    DIFF_BR(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.BR)),

    /**
     * 馬主マスタの差分
     */
    DIFF_BN(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.BN)),

    /**
     * レコード情報の差分
     */
    DIFF_RC(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.RC)),

    /**
     * 地方、海外レースのレース番組情報
     * <p>
     * セットアップでこの条件は、利用できない
     */
    DIFF_RA(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.RA)),

    /**
     * 地方、海外レースの馬毎レース情報
     * <p>
     * セットアップでこの条件は、利用できない
     */
    DIFF_SE(new StoredOpenCondition(StoredDataSpec.DIFF, RecordSpec.SE)),

    /**
     * 繁殖牝馬マスタ
     */
    BLOD_HN(new StoredOpenCondition(StoredDataSpec.BLOD, RecordSpec.HN)),

    /**
     * 産駒マスタ
     */
    BLOD_SK(new StoredOpenCondition(StoredDataSpec.BLOD, RecordSpec.SK)),

    /**
     * 系統情報
     */
    BLOD_BT(new StoredOpenCondition(StoredDataSpec.BLOD, RecordSpec.BT)),

    /**
     * 開催スケジュール情報
     */
    YSCH_YS(new StoredOpenCondition(StoredDataSpec.YSCH, RecordSpec.YS)),

    /**
     * 競走馬市場取引価格
     */
    HOSE_HS(new StoredOpenCondition(StoredDataSpec.HOSE, RecordSpec.HS)),

    /**
     * コース情報
     */
    COMM_CS(new StoredOpenCondition(StoredDataSpec.COMM, RecordSpec.CS)),

    ;


    private final StoredOpenCondition storedOpenCondition;

    JvStored(StoredOpenCondition storedOpenCondition) {
        this.storedOpenCondition = storedOpenCondition;
    }

    public StoredOpenCondition get() {
        return this.storedOpenCondition;
    }

}
