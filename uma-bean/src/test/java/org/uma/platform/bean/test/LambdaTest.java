package org.uma.platform.bean.test;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LambdaTest {

    static class Asset {
        public enum AssetType {STOCK, BOND}

        private final AssetType type;
        private final int value;

        public Asset(AssetType type, int value) {
            this.type = type;
            this.value = value;
        }

        public AssetType getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
    }

    public static int totalAssetValues(final List<Asset> assets) {
        return assets.stream()
                .mapToInt(Asset::getValue)
                .sum();
    }


    public static int totalAssetValues(final List<Asset> assets, final Predicate<Asset> predicate) {
        return assets.stream()
                .filter(predicate)
                .mapToInt(Asset::getValue)
                .sum();
    }


    @Test
    public void test() {
        final List<Asset> assets = Arrays.asList(
                new Asset(Asset.AssetType.BOND, 1000),
                new Asset(Asset.AssetType.BOND, 2000),
                new Asset(Asset.AssetType.STOCK, 3000),
                new Asset(Asset.AssetType.STOCK, 4000)
        );

        System.out.println(totalAssetValues(assets));

        // リファクタリング後
        System.out.println(totalAssetValues(assets, asset -> true));
        // リファクタリング後
        System.out.println(totalAssetValues(assets, asset -> asset.getType() == Asset.AssetType.BOND));

    }

    public static class CalculateNAV {

        private Function<String, BigDecimal> priceFinder;

        public CalculateNAV(final Function<String, BigDecimal> priceFinder) {
            this.priceFinder = priceFinder;
        }

        public BigDecimal computeStockWorth(final String ticker, final int shares) {
            // multiply : 掛け算
            return priceFinder
                    .apply(ticker)
                    .multiply(BigDecimal.valueOf(shares));
        }
    }

    public static class YahooFinance {
        public static BigDecimal getPrice(final String ticket) {
            try {
                final URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + ticket);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                final String data = reader.lines().skip(1).findFirst().get();
                final String[] dataItem = data.split(",");
                return new BigDecimal(dataItem[dataItem.length - 1]);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Test
    void computeStockWorthTest() {
        final CalculateNAV calculateNAV = new CalculateNAV(ticker -> new BigDecimal("6.01"));

        BigDecimal expect = new BigDecimal("6010.00");

        assertEquals(0,
                calculateNAV.computeStockWorth("GooG", 1000).compareTo(expect),
                0.001
        );
    }

    @Test
    void useCalculateNAV(){
        // YahooFinanceクラスのgetPriceメソッド参照を渡してあげる。＝委譲する。
        final CalculateNAV calculateNAV = new CalculateNAV(YahooFinance::getPrice);
        calculateNAV.computeStockWorth("GOOG", 100);

    }

}
