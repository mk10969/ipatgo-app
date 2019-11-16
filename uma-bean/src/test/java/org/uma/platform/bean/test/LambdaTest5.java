package org.uma.platform.bean.test;

import java.util.function.Supplier;

public class LambdaTest5 {
    // virtual proxy パターン

    public static class Holder {
        private Supplier<Heavy> heavy = () -> createAndCacheHeavy();

        public Holder() {
            System.out.println("Holder create");
        }

        public Heavy getHeavy() {
            return heavy.get();
        }

        // proxy
        private synchronized Heavy createAndCacheHeavy() {
            class HeavyFactory implements Supplier<Heavy> {
                private final Heavy HeavyInstance = new Heavy();
                @Override
                public Heavy get() {
                    return HeavyInstance;
                }
            }

            if(!(heavy instanceof HeavyFactory)) {
                heavy = new HeavyFactory();
            }
            return heavy.get();
        }
    }

    public static class Heavy {
        public Heavy() {
            System.out.println("Heavy crete!!!");
        }
    }

}
