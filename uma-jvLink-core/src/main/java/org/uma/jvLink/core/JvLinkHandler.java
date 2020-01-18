package org.uma.jvLink.core;

import org.uma.jvLink.core.exception.JvLinkErrorCode;
import org.uma.jvLink.core.exception.JvLinkRuntimeException;
import org.uma.jvLink.core.response.JvResult;

import java.util.function.Supplier;

class JvLinkHandler {

    private JvLinkHandler() {
    }

    static <T extends JvResult> T handle(Supplier<T> supplier) {
        final T jvResult = supplier.get();

        // リターンコード
        //  0: 正常終了
        // -100 以下: エラー
        if (jvResult.getReturnCode() != 0) {
            throw new JvLinkRuntimeException(JvLinkErrorCode.of(jvResult.getReturnCode()));
        }
        // リータンコードが、0の場合、正常結果を返す。
        return jvResult;

    }
}
