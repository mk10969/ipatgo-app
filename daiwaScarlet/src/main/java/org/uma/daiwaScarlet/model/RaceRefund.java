package org.uma.daiwaScarlet.model;

import lombok.Data;
import org.uma.daiwaScarlet.code.RaceCourseCode;
import org.uma.vodka.config.spec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link org.uma.vodka.config.spec.RecordSpec.HR}
 */

@Data
public class RaceRefund {

    private RecordSpec recordTypeId;
    private String dataDiv;
    private LocalDate dataCreateDate;
    private Integer holdingYear;
    private String holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer entryCount;
    private Integer starterCount;
    private Integer failureFlgWin;
    private Integer failureFlgPlace;
    private Integer failureFlgBracket;
    private Integer failureFlgQuinella;
    private Integer failureFlgQuinellaPlace;
    private String spare1;
    private Integer failureFlgExacta;
    private Integer failureFlgTrio;
    private Integer failureFlgTrifecta;
    private Integer specialRefundFlgWin;
    private Integer specialRefundFlgPlace;
    private Integer specialRefundFlgBracket;
    private Integer specialRefundFlgQuinella;
    private Integer specialRefundFlgQuinellaPlace;
    private String spare2;
    private Integer specialRefundFlgExacta;
    private Integer specialRefundFlgTrio;
    private Integer specialRefundFlgTrifecta;
    private Integer restoreFlgWin;
    private Integer restoreFlgPlace;
    private Integer restoreFlgBracket;
    private Integer restoreFlgQuinella;
    private Integer restoreFlgQuinellaPlace;
    private String spare3;
    private Integer restoreFlgExacta;
    private Integer restoreFlgTrio;
    private Integer restoreFlgTrifecta;

    private List<Integer> restoreHorseNos;
    private List<Integer> restoreBrackets;
    private List<Integer> restoreSameBrackets;

    // 単勝
    private List<refundWin> refundWins;
    // 複勝
    private List<refundPlace> refundPlaces;
    // 枠連
    private List<refundBracketQuinella> refundBracketQuinellas;
    // 馬連
    private List<refundQuinella> refundQuinellas;
    // ワイド
    private List<refundQuinellaPlace> refundQuinellaPlaces;
    // 予備
    private List<refundSpare> refundSpares;
    // 馬単
    private List<refundExacta> refundExactas;
    // 3連複
    private List<refundTrio> refundTrios;
    // 3連単
    private List<refundTrifecta> refundTrifectas;


    /** 単勝 */
    @Data
    private static class refundWin {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

    /** 複勝 */
    @Data
    private static class refundPlace {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

    /** 枠連 */
    @Data
    private static class refundBracketQuinella {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

    /** 馬連 */
    @Data
    private static class refundQuinella {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

    /** ワイド */
    @Data
    private static class refundQuinellaPlace {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

    /** 予備 */
    @Data
    private static class refundSpare {
        private String space1;
        private String space2;
        private String space3;
    }

    /** 馬単 */
    @Data
    private static class refundExacta {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

    /** ３連複 */
    @Data
    private static class refundTrio {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

    /** ３連単 */
    @Data
    private static class refundTrifecta {
        private Integer horseNo;
        private Long refundMoney;
        private Integer betRank;
    }

}
