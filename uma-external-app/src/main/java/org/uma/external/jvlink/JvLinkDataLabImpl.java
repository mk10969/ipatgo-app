package org.uma.external.jvlink;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.Variant;
import org.uma.external.jvlink.response.JvByteContent;
import org.uma.external.jvlink.response.JvOpenResult;
import org.uma.external.jvlink.response.JvSimpleResult;
import org.uma.external.jvlink.response.JvStringContent;

class JvLinkDataLabImpl implements JvLinkDataLab {

    /**
     * JvLink COM
     */
    private static final String JVLINK_DLL = "JVDTLab.JVLink.1";

    /**
     * ActiveXComponent instance
     */
    private final ActiveXComponent activeXComponent;

    /**
     * Event instance
     */
    private DispatchEvents dispatchEvents;


    JvLinkDataLabImpl() {
        this.activeXComponent = new ActiveXComponent(JVLINK_DLL);
    }


    @Override
    public JvSimpleResult jvInit(String sid) {
        Variant variant = Dispatch.call(activeXComponent, "JVInit", sid);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
    public JvSimpleResult jvSetUIProperties() {
        Variant variant = Dispatch.call(activeXComponent, "JVSetUIProperties");
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
    public JvSimpleResult jvSetServiceKey(String serviceKey) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetServiceKey", serviceKey);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
    public JvSimpleResult jvSetSaveFlag(int saveFlag) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSaveFlag", saveFlag);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
    public JvSimpleResult jvSetSavePath(String savePath) {
        Variant variant = Dispatch.call(activeXComponent, "JVSetSavePath", savePath);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
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

    @Override
    public JvSimpleResult jvRtOpen(String dataSpec, String key) {
        Variant variant = Dispatch.call(activeXComponent, "JVRTOpen", dataSpec, key);
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
    public JvStringContent jvRead(int size) {
        Variant buff = new Variant("", true);
        Variant filename = new Variant("", true);
        Variant variant = Dispatch.call(activeXComponent, "JVRead", buff, size, filename);
        return JvStringContent.create(variant.getInt())
                .line(buff.getStringRef())
                .fileName(filename.getStringRef())
                .build();
    }

    @Override
    @Deprecated
    public JvByteContent jvGets(int size) {
        // 処理が遅いため、実装しない。
        return null;
    }

    @Override
    public JvSimpleResult jvStatus() {
        Variant variant = Dispatch.call(activeXComponent, "JVStatus");
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
    public void jvClose() {
        Dispatch.call(activeXComponent, "JVClose");
    }

    @Override
    public void jvSkip() {
        Dispatch.call(activeXComponent, "JVSkip");
    }

    @Override
    public void jvCancel() {
        Dispatch.call(activeXComponent, "JVCancel");
    }


    @Override
    public JvSimpleResult jvWatchEvent(JvLinkEventHandler jvLinkEventHandler) {
        dispatchEvents = new DispatchEvents(activeXComponent, new JvLinkEventCallback(jvLinkEventHandler), JVLINK_DLL);
        Variant variant = Dispatch.call(activeXComponent, "JVWatchEvent");
        return JvSimpleResult.create(variant.getInt()).build();
    }

    @Override
    public JvSimpleResult jvWatchEventClose() {
        if (dispatchEvents != null) {
            dispatchEvents.safeRelease();
        }
        Variant variant = Dispatch.call(activeXComponent, "JVWatchEventClose");
        return JvSimpleResult.create(variant.getInt()).build();
    }

}