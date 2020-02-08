package org.uma.extarnal.jvlink.client;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.uma.extarnal.jvlink.client.response.JvByteContent;
import org.uma.extarnal.jvlink.client.response.JvOpenResult;
import org.uma.extarnal.jvlink.client.response.JvSimpleResult;
import org.uma.extarnal.jvlink.client.response.JvStringContent;

class JvLinkDataLabImpl implements JvLinkDataLab {

    private final ActiveXComponent activeXComponent;
    private static final String JVLINK_DLL = "JVDTLab.JVLink.1";

    JvLinkDataLabImpl() {
        this.activeXComponent = new ActiveXComponent(JVLINK_DLL);
    }


    public JvSimpleResult jvInit(String sid) {
        Variant variant = Dispatch.call(activeXComponent, "JVInit", sid);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    public JvSimpleResult jvSetUIProperties() {
        Variant variant = Dispatch.call(activeXComponent, "JVSetUIProperties");
        return JvSimpleResult.create(variant.getInt()).build();
    }

    public JvSimpleResult jvSetServiceKey(String serviceKey) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetServiceKey", serviceKey);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    public JvSimpleResult jvSetSaveFlag(int saveFlag) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSaveFlag", saveFlag);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    public JvSimpleResult jvSetSavePath(String savePath) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSavePath", savePath);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    public JvOpenResult jvOpen(String dataSpec, String fromTime, int option) {
        Variant readCount = new Variant(0, true);
        Variant downloadCount = new Variant(0, true);
        Variant lastFileTimeStamp = new Variant("", true);
        Variant variant = Dispatch.call(activeXComponent, "JVOpen", dataSpec, fromTime, option, readCount, downloadCount, lastFileTimeStamp);
        return JvOpenResult.create(variant.getInt())
                .readCount(readCount.getIntRef())
                .downloadCount(downloadCount.getIntRef())
                .lastFileTimeStamp(lastFileTimeStamp.getStringRef())
                .build();
    }

    public JvSimpleResult jvRtOpen(String dataSpec, String key) {
        Variant variant = Dispatch.call(activeXComponent, "JVRTOpen", dataSpec, key);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    public JvStringContent jvRead(int size) {
        Variant buff = new Variant("", true);
        Variant filename = new Variant("", true);
        Variant variant = Dispatch.call(activeXComponent, "JVRead", buff, size, filename);
        return JvStringContent.create(variant.getInt())
                .line(buff.getStringRef())
                .fileName(filename.getStringRef())
                .build();
    }

    @Deprecated
    public JvByteContent jvGets(int size) {
        // 処理が遅いため、実装しない。
        return null;
    }

    public JvSimpleResult jvStatus() {
        Variant variant = Dispatch.call(activeXComponent, "JVStatus");
        return JvSimpleResult.create(variant.getInt()).build();
    }

    public void jvClose() {
        Dispatch.call(activeXComponent, "JVClose");
    }

    public void jvSkip() {
        Dispatch.call(activeXComponent, "JVSkip");
    }

    public void jvCancel() {
        Dispatch.call(activeXComponent, "JVCancel");
    }


}