package org.uma.platform.jvlink;

import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.Variant;

public class JvLInkDataLabComponent extends COMLateBindingObject {


    public JvLInkDataLabComponent(String progId) throws COMException {
        super(progId, true);
    }

    public Variant.VARIANT call(String methodName, String arg) {
        return super.invoke(methodName, new Variant.VARIANT(arg));
    }








    public void test(){
        JvLInkDataLabComponent com = new JvLInkDataLabComponent("aaaaa");

        // release
    }

}
