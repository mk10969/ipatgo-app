package org.uma.platform.feed.application.jvlink;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class JvLinkTest2 {

    @Test
    void test_Mono_create() {
        // NullPOも拾ってくれるのね。
        String str = "aaa";
        throwNullPo(str).subscribe(
                i -> System.out.println(i),
                e -> e.printStackTrace(),
                () -> System.out.println("完了")
        );
    }

    public Mono<String> throwNullPo(String str) {
        return Mono.create(sink -> {
            Objects.requireNonNull(str);
            try {
                sink.success(getName(str));
            } catch (Exception e) {
                // ここでスタックとレース吐かなくても、
                // subscribe側で吐けるよ。
                sink.error(e);
            }
        });
    }

    private String getName(String str) throws Exception {
        throw new Exception("ERROR");
    }


}
