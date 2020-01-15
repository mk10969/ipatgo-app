package org.uma.platform.jvlink;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.NotImplementedException;
import com.jacob.com.Variant;
import org.uma.platform.jvlink.response.JvByteContent;
import org.uma.platform.jvlink.response.JvOpenResult;
import org.uma.platform.jvlink.response.JvSimpleResult;
import org.uma.platform.jvlink.response.JvStringContent;

class JvLinkDataLabImpl implements JvLinkDataLab {

    private final ActiveXComponent activeXComponent;
    private static final String JVLINK_DLL = "JVDTLab.JVLink.1";

    JvLinkDataLabImpl() {
        this.activeXComponent = new ActiveXComponent(JVLINK_DLL);
    }


    public JvSimpleResult jvInit(String sid) {
        Variant variant = Dispatch.call(activeXComponent, "JVInit", sid);
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public JvSimpleResult jvSetUIProperties() {
        Variant variant = Dispatch.call(activeXComponent, "JVSetUIProperties");
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public JvSimpleResult jvSetServiceKey(String serviceKey) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetServiceKey", serviceKey);
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public JvSimpleResult jvSetSaveFlag(int saveFlag) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSaveFlag", saveFlag);
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public JvSimpleResult jvSetSavePath(String savePath) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSavePath", savePath);
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public JvOpenResult jvOpen(String dataSpec, String fromTime, int option) {
        Variant readCount = new Variant(0, true);
        Variant downloadCount = new Variant(0, true);
        Variant lastFileTimeStamp = new Variant("", true);
        Variant variant = Dispatch.call(activeXComponent, "JVOpen", dataSpec, fromTime, option, readCount, downloadCount, lastFileTimeStamp);
        return JvOpenResult.create(getInt(variant))
                .readCount(getIntRef(readCount))
                .downloadCount(getIntRef(downloadCount))
                .lastFileTimeStamp(getStringRef(lastFileTimeStamp))
                .build();
    }

    public JvSimpleResult jvRtOpen(String dataSpec, String key) {
        Variant variant = Dispatch.call(activeXComponent, "JVRTOpen", dataSpec, key);
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public JvStringContent jvRead(int size) {
        Variant buff = new Variant("", true);
        Variant filename = new Variant("", true);
        Variant variant = Dispatch.call(activeXComponent, "JVRead", buff, size, filename);
        return JvStringContent.create(getInt(variant))
                .line(getStringRef(buff))
                .fileName(getStringRef(filename))
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
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public JvSimpleResult jvClose() {
        Variant variant = Dispatch.call(activeXComponent, "JVClose");
        return JvSimpleResult.create(getInt(variant)).build();
    }

    public void jvSkip() {
        Dispatch.call(activeXComponent, "JVSkip");
    }

    public void jvCancel() {
        Dispatch.call(activeXComponent, "JVCancel");
    }

    /**
     * Variantリソースの解放忘れでメモリがリークした。
     * そのため、Variantインスタンスから、Javaオブジェクト
     * に変換する際は、同時にリソースの解放も行う。
     * try-finallyの方はよかったな。
     */
    private static int getInt(Variant variant) {
        int n = variant.getInt();
//        variant.safeRelease();
        ComThread.Release();
        return n;
    }

    private static int getIntRef(Variant variant) {
        int n = variant.getIntRef();
//        variant.safeRelease();
        ComThread.Release();
        return n;
    }

    private static String getStringRef(Variant variant) {
        String n = variant.getStringRef();
//        variant.safeRelease();
        ComThread.Release();
        return n;
    }


}
