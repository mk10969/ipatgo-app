package org.uma.external.ipatgo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class IPatGoClient {

    private static final int timeout = 5;

    /**
     * IPATGOは、標準出力されるのものがないので、全て吐き捨てる。
     *
     * @param command
     * @return
     */
    public static CompletableFuture<Integer> execute(final List<String> command) {
        return CompletableFuture.supplyAsync(() -> startProcess(command))
                .thenApplyAsync(process -> {
                    try {
                        // 5秒でタイムアウト設定
                        boolean result = process.waitFor(timeout, TimeUnit.SECONDS);
                        if (!result) {
                            throw new IPatGoException("IpatGo TimeoutException: [cmd: "
                                    + command + "]");
                        }
                        return process.exitValue();

                    } catch (InterruptedException e) {
                        throw new IPatGoException("IpatGo InterruptedException: [cmd: "
                                + command + "]", e);

                    } finally {
                        if (process.isAlive()){
                            process.destroy();
                        }
                    }
                });
    }

    private static Process startProcess(final List<String> cmdAndParams) {
        try {
            return new ProcessBuilder(cmdAndParams)
                    .redirectErrorStream(true)
                    .start();
        } catch (IOException e) {
            throw new IPatGoException("IpatGo launch failed: [cmd: "
                    + cmdAndParams + "]", e);
        }
    }

}
