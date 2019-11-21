package org.uma.platform.feed.application.jvlink;

import com.jacob.com.Variant;
import org.uma.platform.feed.application.jvlink.response.JvResult;
import org.uma.platform.common.utils.reflection.ClassUtil;

public class JvResultFactory {

    private JvResultFactory() {
    }

    static <T extends JvResult.Builder> T create(Class<T> type, Variant variant) {
        T t = ClassUtil.newInstance(type, variant.getInt());
        // 解放
        variant.safeRelease();
        return t;
    }

}
