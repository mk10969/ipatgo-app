package org.uma.platform.jvlink;

import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.Variant.VARIANT;

class JvLInkDataLabComponent extends COMLateBindingObject {


    public JvLInkDataLabComponent(String progId) throws COMException {
        super(progId, true);
    }

    public VARIANT call(String methodName) {
        return super.invoke(methodName);
    }

    public VARIANT call(String methodName, String arg) {
        return super.invoke(methodName, new VARIANT(arg));
    }

    public VARIANT call(String methodName, int arg) {
        return super.invoke(methodName, new VARIANT(arg));
    }

    public VARIANT call(String methodName, String arg1, String arg2, int arg3,
                        VARIANT arg4, VARIANT arg5, VARIANT arg6) {
        VARIANT variant1 = new VARIANT(arg1);
        VARIANT variant2 = new VARIANT(arg2);
        VARIANT variant3 = new VARIANT(arg3);
        return super.invoke(methodName, new VARIANT[]{variant1, variant2, variant3, arg4, arg5, arg6});
    }

    public VARIANT call(String methodName, String arg1, String arg2) {
        return super.invoke(methodName, new VARIANT(arg1), new VARIANT(arg2));
    }

    public VARIANT call(String methodName, VARIANT arg1, VARIANT arg2) {
        return super.invoke(methodName, arg1, arg2);
    }


}
