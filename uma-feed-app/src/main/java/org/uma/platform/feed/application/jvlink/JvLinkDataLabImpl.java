package org.uma.platform.feed.application.jvlink;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.NotImplementedException;
import com.jacob.com.Variant;
import org.uma.platform.feed.application.jvlink.response.JvByteContent;
import org.uma.platform.feed.application.jvlink.response.JvOpenResult;
import org.uma.platform.feed.application.jvlink.response.JvSimpleResult;
import org.uma.platform.feed.application.jvlink.response.JvStringContent;

class JvLinkDataLabImpl implements JvLinkDataLab {

    private final ActiveXComponent activeXComponent;
    private static final String JVLINK_DLL = "JVDTLab.JVLink.1";

    JvLinkDataLabImpl() {
        this.activeXComponent = new ActiveXComponent(JVLINK_DLL);
    }

    public JvSimpleResult jvInit(String sid) {
        Variant variant = Dispatch.call(activeXComponent, "JVInit", sid);
        //Log.info(String.format("JRA-VANサーバにアクセスする際に %s をUserAgentとして使用します。", sid));
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public JvSimpleResult jvSetUIProperties() {
        Variant variant = Dispatch.call(activeXComponent, "JVSetUIProperties");
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public JvSimpleResult jvSetServiceKey(String serviceKey) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetServiceKey", serviceKey);
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public JvSimpleResult jvSetSaveFlag(int saveFlag) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSaveFlag", saveFlag);
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public JvSimpleResult jvSetSavePath(String savePath) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSavePath", savePath);
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public JvOpenResult jvOpen(String dataSpec, String fromTime, int option) {
        Variant readCount = new Variant(0, true);
        Variant downloadCount = new Variant(0, true);
        Variant lastFileTimeStamp = new Variant("", true);
        Variant variant = Dispatch.call(activeXComponent, "JVOpen", dataSpec, fromTime, option, readCount, downloadCount, lastFileTimeStamp);
        return JvResultFactory
                .create(JvOpenResult.Builder.class, variant)
                .downloadCount(downloadCount.getIntRef())
                .readCount(readCount.getIntRef())
                .lastFileTimeStamp(lastFileTimeStamp.getStringRef())
                .build();
    }

    public JvSimpleResult jvRtOpen(String dataSpec, String key) {
        Variant variant = Dispatch.call(activeXComponent, "JVRTOpen", dataSpec, key);
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public JvStringContent jvRead(int size) {
        Variant vBuff = new Variant("", true);
        Variant vFileName = new Variant("", true);
        Variant variant = Dispatch.call(activeXComponent, "JVRead", vBuff, size, vFileName);
        return JvResultFactory
                .create(JvStringContent.Builder.class, variant)
                .line(vBuff.getStringRef())
                .fileName(vFileName.getStringRef())
                .build();
    }

    @Deprecated
    public JvByteContent jvGets(int size) {
        // 処理が遅いため、実装しない。
        throw new NotImplementedException("JVGets Not Impl.");
//        SafeArray safeArray = JacobUtil.getSafeByteArray();
//        Variant vBuff = JacobUtil.getArrayRefVariant(safeArray);
//        Variant vFileName = new Variant("", true);
//        Variant variant = Dispatch.call(activeXComponent, "JVGets", vBuff, size, vFileName);
//        byte[] byteArray = new byte[size];
//        // 終端を含めない。 0 <= x < size
//        IntStream.range(0, size).forEach(i -> byteArray[i] = safeArray.getByte(i));
//        return JvResultFactory
//                .create(JvByteContent.Builder.class, variant)
//                .line(byteArray)
//                .fileName(vFileName.getStringRef())
//                .build();
    }

    public JvSimpleResult jvStatus() {
        Variant variant = Dispatch.call(activeXComponent, "JVStatus");
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public JvSimpleResult jvClose() {
        Variant variant = Dispatch.call(activeXComponent, "JVClose");
        return JvResultFactory
                .create(JvSimpleResult.Builder.class, variant)
                .build();
    }

    public void jvSkip() {
        Dispatch.call(activeXComponent, "JVSkip");
    }

    public void jvCancel() {
        Dispatch.call(activeXComponent, "JVCancel");
    }

}
