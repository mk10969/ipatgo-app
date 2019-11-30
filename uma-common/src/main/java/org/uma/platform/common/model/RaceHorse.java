package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.*;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.UM}
 */

@Data
public class RaceHorse {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * 血統登録番号 10桁
     */
    private Long bloodlineNo;
    private Boolean raceHorseEraseDiv;
    private LocalDate raceHorseRegistDate;
    private LocalDate raceHorseEraseDate;
    private LocalDate birthDate;
    private String horseName;
    private String horseNameHalfKana;
    private String horseNameEng;
    private Integer jraFacilityStayFlg;
    private String spare1;
    private HorseSignCode horseSignCd;
    private SexCode sexCd;
    private BreedCode breedCd;
    private HairColorCode hairColorCd;

    private List<Breeding> BreedingNo3rd;
    private EastOrWestBelongCode ewBelongCd;

    private Integer trainerCd;
    private String trainerNameShort;
    private String invitationAreaName;

    private Integer breederCd;
    private String breederNameWithoutCorp;
    private String sourceAreaName;

    private Integer ownerCd;
    private String ownerNameWithoutCorp;

    private Long addedMoneyTotalFlat;
    private Long addedMoneyTotalObstacle;
    private Long stakesMoneyTotalFlat;
    private Long stakesMoneyTotalObstacle;
    private Long allMoneyTotalFlat;
    private Long allMoneyTotalObstacle;


    @Data
    private static class Breeding {
        /**
         * 繁殖登録番号 8桁
         * {@link BreedingHorse.breedingNo}
         */
        private Integer breedingNo;
        private String horseName;

    }

}
