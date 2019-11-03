package org.uma.vodka;

import com.jacob.com.Variant;
import org.uma.vodka.common.reflection.ClassUtil;
import org.uma.vodka.response.JvResult;

class JvResultFactory {

    private JvResultFactory() {
    }

    static <T extends JvResult.Builder> T create(Class<T> type, Variant variant) {
        T t = ClassUtil.newInstance(type, variant.getInt());
        // 解放
        variant.safeRelease();
        return t;
    }

}
