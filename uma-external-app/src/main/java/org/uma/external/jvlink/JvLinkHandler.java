package org.uma.external.jvlink;

import org.uma.external.jvlink.exception.JvLinkErrorCode;
import org.uma.external.jvlink.exception.JvLinkRuntimeException;
import org.uma.external.jvlink.response.JvResult;

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
