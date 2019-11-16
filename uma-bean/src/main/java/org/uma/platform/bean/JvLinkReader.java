package org.uma.platform.bean;

import org.uma.platform.common.utils.lang.ThreadUtil;
import org.uma.platform.bean.exception.JvLinkErrorCode;
import org.uma.platform.bean.exception.JvLinkRuntimeException;
import org.uma.platform.bean.response.JvResult;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class JvLinkReader<T extends JvResult> implements Iterable<T> {

    private final Supplier<T> jvGenerator;

    JvLinkReader(Supplier<T> supplier) {
        this.jvGenerator = Objects.requireNonNull(supplier);
    }

    Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator(), Spliterator.NONNULL), false);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int returnCode = -1;

            @Override
            public boolean hasNext() {
                // 次があれば、True、終了であれば、False。
                return returnCode != 0;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T jvContent = jvGenerator.get();
                returnCode = jvContent.getReturnCode();
                // リターンコード
                //  1 以上: データサイズ
                //  0: おしまい
                // -1: 処理待ち or 該当データがない。
                // -2: ユーザキャンセル。
                // -3: サーバからファイルダウンロード待ち。
                // -100 以下: エラー
                if (returnCode < -1) {
                    if (returnCode == -3) {
                        // 1秒待機
                        ThreadUtil.sleep(1000);
                        // もう一回
                        return next();
                    }
                    throw new JvLinkRuntimeException(JvLinkErrorCode.of(returnCode));
                }
                return jvContent;
            }
        };
    }

}
