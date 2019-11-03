package org.uma.vodka;


import org.uma.vodka.response.JvByteContent;
import org.uma.vodka.response.JvOpenResult;
import org.uma.vodka.response.JvSimpleResult;
import org.uma.vodka.response.JvStringContent;

interface JvLinkDataLab {

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

    JvSimpleResult jvClose();

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
//    void jvWatchEvent(JvLinkEventHandler eventHandler);
//
//    JvOpenResult jvWatchEventClose();

}