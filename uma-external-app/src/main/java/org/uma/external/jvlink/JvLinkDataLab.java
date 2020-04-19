package org.uma.external.jvlink;

import org.uma.external.jvlink.response.JvByteContent;
import org.uma.external.jvlink.response.JvOpenResult;
import org.uma.external.jvlink.response.JvSimpleResult;
import org.uma.external.jvlink.response.JvStringContent;

public interface JvLinkDataLab {

    JvSimpleResult jvInit(String sid);

    JvSimpleResult jvSetUIProperties();

    JvSimpleResult jvSetServiceKey(String serviceKey);

    JvSimpleResult jvSetSaveFlag(int saveFlag);

    JvSimpleResult jvSetSavePath(String savePath);

    JvOpenResult jvOpen(String dataSpec, String fromTime, int option);

    JvSimpleResult jvRtOpen(String dataSpec, String key);

    JvStringContent jvRead(int size);

    JvByteContent jvGets(int size);

    JvSimpleResult jvStatus();

    void jvSkip();

    void jvCancel();

    void jvClose();

    void jvWatchEvent(JvLinkEventHandler eventHandler);

    JvSimpleResult jvWatchEventClose();


    /**
     * JvLink Event通知系のハンドラーインターフェース
     */
    interface JvLinkEventHandler {

        /**
         * 払戻確定イベントをハンドリングします。
         *
         * @param yyyymmddjjrr パラメータ
         */
        void handlePay(String yyyymmddjjrr);

        /**
         * 馬体重発表イベントをハンドリングします。
         *
         * @param yyyymmddjjrr パラメータ
         */
        void handleWeight(String yyyymmddjjrr);

        /**
         * 騎手変更イベントをハンドリングします。
         *
         * @param ttyyyymmddjjrrnnnnnnnnnnnnnn パラメータ
         */
        void handleJockeyChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn);

        /**
         * 天候馬場状態変更イベントをハンドリングします。
         *
         * @param ttyyyymmddjjrrnnnnnnnnnnnnnn パラメータ
         */
        void handleWeather(String ttyyyymmddjjrrnnnnnnnnnnnnnn);

        /**
         * コース変更イベントをハンドリングします。
         *
         * @param ttyyyymmddjjrrnnnnnnnnnnnnnn パラメータ
         */
        void handleCourseChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn);

        /**
         * 出走取消・競走除外イベントをハンドリングします。
         *
         * @param ttyyyymmddjjrrnnnnnnnnnnnnnn パラメータ
         */
        void handleAvoid(String ttyyyymmddjjrrnnnnnnnnnnnnnn);

        /**
         * 発走時刻変更イベントをハンドリングします。
         *
         * @param ttyyyymmddjjrrnnnnnnnnnnnnnn パラメータ
         */
        void handleTimeChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn);
    }


//    下記のインターフェースは、必要になれば、実装する予定
//
//    JvOpenResult jvFileDelete(String fileName);
//
//    JvOpenResult jvFukuFile(String pattern, String filePath);
//
//    JvOpenResult jvMvCheck(String key);
//
//    JvOpenResult jvMvPlay(String key);
//
//    JvOpenResult jvMvPlayWithTime(String movieType, String key);
//
//    JvOpenResult jvMvOpen(String movieType, String searchKey);
//
//    JvMvContents jvMvRead(long size);
//
//    JvCourseFile jvCourseFile(String key);
//
//    JvCourseFile jvCourceFile2(String key, String filePath);
//

}