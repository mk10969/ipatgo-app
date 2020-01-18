package org.uma.jvLink.core;

import org.uma.jvLink.core.config.condition.StoredOpenCondition;

import static org.uma.jvLink.core.config.spec.RecordSpec.*;
import static org.uma.jvLink.core.config.spec.StoredDataSpec.*;

public enum JvStored {

    ///// 蓄積系データ /////

    /**
     * レース詳細
     */
    RACE_RA(new StoredOpenCondition(RACE, RA)),

    /**
     * 出走馬詳細
     */
    RACE_SE(new StoredOpenCondition(RACE, SE)),

    /**
     * レース払戻
     */
    RACE_HR(new StoredOpenCondition(RACE, HR)),

    /**
     * 確定票数（3連単以外）
     */
    RACE_H1(new StoredOpenCondition(RACE, H1)),

    /**
     * 確定票数（3連単）
     */
    RACE_H6(new StoredOpenCondition(RACE, H6)),

    /**
     * 確定オッズ（単複枠）
     */
    RACE_O1(new StoredOpenCondition(RACE, O1)),

    /**
     * 確定オッズ（馬連）
     */
    RACE_O2(new StoredOpenCondition(RACE, O2)),

    /**
     * 確定オッズ（ワイド）
     */
    RACE_O3(new StoredOpenCondition(RACE, O3)),

    /**
     * 確定オッズ（馬単）
     */
    RACE_O4(new StoredOpenCondition(RACE, O4)),

    /**
     * 確定オッズ（３連複）
     */
    RACE_O5(new StoredOpenCondition(RACE, O5)),

    /**
     * 確定オッズ（３連単）
     */
    RACE_O6(new StoredOpenCondition(RACE, O6)),

    /**
     * WIN5
     */
    RACE_WF(new StoredOpenCondition(RACE, WF)),

    /**
     * 競走馬除外情報
     */
    RACE_JG(new StoredOpenCondition(RACE, JG)),

    /**
     * 競走馬のマスタの差分
     */
    DIFF_UM(new StoredOpenCondition(DIFF, UM)),

    /**
     * 騎手マスタの差分
     */
    DIFF_KS(new StoredOpenCondition(DIFF, KS)),

    /**
     * 調教師マスタの差分
     */
    DIFF_CH(new StoredOpenCondition(DIFF, CH)),

    /**
     * 生産者マスタの差分
     */
    DIFF_BR(new StoredOpenCondition(DIFF, BR)),

    /**
     * 馬主マスタの差分
     */
    DIFF_BN(new StoredOpenCondition(DIFF, BN)),

    /**
     * レコード情報の差分
     */
    DIFF_RC(new StoredOpenCondition(DIFF, RC)),

    /**
     * 地方、海外レースのレース番組情報
     * <p>
     * セットアップでこの条件は、利用できない
     */
    DIFF_RA(new StoredOpenCondition(DIFF, RA)),

    /**
     * 地方、海外レースの馬毎レース情報
     * <p>
     * セットアップでこの条件は、利用できない
     */
    DIFF_SE(new StoredOpenCondition(DIFF, SE)),

    /**
     * 繁殖牝馬マスタ
     */
    BLOD_HN(new StoredOpenCondition(BLOD, HN)),

    /**
     * 産駒マスタ
     */
    BLOD_SK(new StoredOpenCondition(BLOD, SK)),

    /**
     * 系統情報
     */
    BLOD_BT(new StoredOpenCondition(BLOD, BT)),

    /**
     * 開催スケジュール情報
     */
    YSCH_YS(new StoredOpenCondition(YSCH, YS)),

    /**
     * 競走馬市場取引価格
     */
    HOSE_HS(new StoredOpenCondition(HOSE, HS)),

    /**
     * コース情報
     */
    COMM_CS(new StoredOpenCondition(COMM, CS)),


    ///// セットアップ用データ /////

    /**
     * RACE ALL IN ONE
     */
    RACE_ALL(new StoredOpenCondition(RACE, null)),

    /**
     * BLOD ALL IN ONE
     */
    BLOD_ALL(new StoredOpenCondition(BLOD, null)),

    /**
     * DIFF ALL IN ONE
     */
    DIFF_ALL(new StoredOpenCondition(DIFF, null)),

    ;


    private final StoredOpenCondition storedOpenCondition;

    JvStored(StoredOpenCondition storedOpenCondition) {
        this.storedOpenCondition = storedOpenCondition;
    }

    public StoredOpenCondition get() {
        return this.storedOpenCondition;
    }

}
