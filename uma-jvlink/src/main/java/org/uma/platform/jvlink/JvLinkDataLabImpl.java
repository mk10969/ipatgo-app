package org.uma.platform.jvlink;

import com.sun.jna.platform.win32.Variant.VARIANT;
import org.uma.platform.jvlink.response.JvByteContent;
import org.uma.platform.jvlink.response.JvOpenResult;
import org.uma.platform.jvlink.response.JvSimpleResult;
import org.uma.platform.jvlink.response.JvStringContent;

class JvLinkDataLabImpl implements JvLinkDataLab {

    private final JvLInkDataLabComponent jvLInkDataLabComponent;
    private static final String JVLINK_DLL = "JVDTLab.JVLink.1";

    JvLinkDataLabImpl() {
        JvLInkDataLabComponent.init();
        this.jvLInkDataLabComponent = new JvLInkDataLabComponent(JVLINK_DLL);
    }


    public JvSimpleResult jvInit(String sid) {
        VARIANT variant = jvLInkDataLabComponent.call("JVInit", sid);
        return JvSimpleResult.create(variant.intValue()).build();
    }

    public JvSimpleResult jvSetUIProperties() {
        VARIANT variant = jvLInkDataLabComponent.call("JVSetUIProperties");
        return JvSimpleResult.create(variant.intValue()).build();
    }

    public JvSimpleResult jvSetServiceKey(String serviceKey) {
        VARIANT variant = jvLInkDataLabComponent.call("JVSetServiceKey", serviceKey);
        return JvSimpleResult.create(variant.intValue()).build();
    }

    public JvSimpleResult jvSetSaveFlag(int saveFlag) {
        VARIANT variant = jvLInkDataLabComponent.call("JVSetSaveFlag", saveFlag);
        return JvSimpleResult.create(variant.intValue()).build();
    }

    public JvSimpleResult jvSetSavePath(String savePath) {
        VARIANT variant = jvLInkDataLabComponent.call("JVSetSavePath", savePath);
        return JvSimpleResult.create(variant.intValue()).build();
    }

    public JvOpenResult jvOpen(String dataSpec, String fromTime, int option) {
        VARIANT readCount = new VARIANT(0);
        VARIANT downloadCount = new VARIANT(0);
        VARIANT lastFileTimeStamp = new VARIANT("");
        VARIANT variant = jvLInkDataLabComponent.call("JVOpen", dataSpec, fromTime, option,
                readCount, downloadCount, lastFileTimeStamp);
        return JvOpenResult.create(variant.intValue())
                .readCount(readCount.intValue())
                .downloadCount(downloadCount.intValue())
                .lastFileTimeStamp(lastFileTimeStamp.stringValue())
                .build();
    }

    public JvSimpleResult jvRtOpen(String dataSpec, String key) {
        VARIANT variant = jvLInkDataLabComponent.call("JVRTOpen", dataSpec, key);
        return JvSimpleResult.create(variant.intValue()).build();
    }

    public JvStringContent jvRead(int size) {
        VARIANT buff = new VARIANT("");
        VARIANT filename = new VARIANT("");
        VARIANT variant = jvLInkDataLabComponent.call("JVRead", buff, filename);
        return JvStringContent.create(variant.intValue())
                .line(buff.stringValue())
                .fileName(filename.stringValue())
                .build();
    }

    @Deprecated
    public JvByteContent jvGets(int size) {
        return null;
    }

    public JvSimpleResult jvStatus() {
        VARIANT variant = jvLInkDataLabComponent.call("JVStatus");
        return JvSimpleResult.create(variant.intValue()).build();
    }

    public void jvClose() {
        jvLInkDataLabComponent.call("JVClose");
        jvLInkDataLabComponent.fin();
    }

    public void jvSkip() {
        jvLInkDataLabComponent.call("JVSkip");
    }

    public void jvCancel() {
        jvLInkDataLabComponent.call("JVCancel");
    }

}
