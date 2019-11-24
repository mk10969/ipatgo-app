package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.*;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.SE}
 */

@Data
public class HorseRacingDetails {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;
    private Integer holdingYear;
    private String holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer bracketNo;

    /**
     * 馬番は、払戻、票数のフィールドと合わせて、String型とする。
     * 01, 02...とデータが、保持されてほしいため。
     */
    private String horseNo;

    /**
     * {@link Offspring.bloodlineNo}
     */
    private Integer bloodlineNo;
    private String horseName;
    private HorseSignCode horseSignCd;
    private SexCode sexCd;
    private BreedCode bredCd;
    private HairColorCode hairColorCd;
    private Integer age;
    private EastOrWestBelongCode ewBelongCd;
    private String trainerCd;
    private String trainerNameShort;
    private String ownerCd;
    private String ownerNameWithoutCorp;
    private String clothingMark;
    private String spare1;
    private Float loadWeight;
    private Float loadWeightBefore;
    private Integer blinkerUseDiv;
    private String spare2;
    private String jockeyCd;
    private String jockeyCdBefore;
    private String jockeyNameShort;
    private String jockeyNameShortBefore;
    private JockeyApprenticeCode jockeyApprenticeCd;
    private JockeyApprenticeCode jockeyApprenticeCdBefore;
    private Integer horseWeight;
    private Character changeSign;
    private Integer changeAmount;
    private AbnormalDivisionCode abnormalDivCd;
    private Integer finishedArrivalOrder;
    private Integer fixedArrivalOrder;
    private Integer deadHeadDiv;
    private Integer deadHeadCount;
    private String runningTime;
    private MarginCode marginCd;
    private MarginCode marginCd2;
    private MarginCode marginCd3;
    private Integer rankCorner1;
    private Integer rankCorner2;
    private Integer rankCorner3;
    private Integer rankCorner4;
    private String oddsWin;
    private Integer betRankWin;
    private Long acquirementAddedMoney;
    private Long acquirementStakesMoney;
    private String spare4;
    private String spare5;
    private Float lastFurlong4;
    private Float lastFurlong3;
    private List<Contender> contenders;
    private String timeMargin;
    private Integer recordUpdateDiv;
    private Integer miningDiv;
    private String miningExpectationRunningTime;
    private String miningExpectationErrorPlus;
    private String miningExpectationErrorMinus;
    private Integer miningExpectationRank;
    private Integer runningStyle;

    @Data
    private static class Contender {
        private Integer bloodRegistrationNo;
        private String horseName;
    }


}
