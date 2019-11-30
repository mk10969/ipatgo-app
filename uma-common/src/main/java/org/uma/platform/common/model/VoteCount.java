package org.uma.platform.common.model;


import lombok.Data;
import org.uma.platform.common.code.RaceCourseCode;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.H1}
 */

@Data
public class VoteCount {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * {@link RacingDetails.raceId}
     */
    private Long raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer entryCount;
    private Integer starterCount;
    private Integer saleFlagWin;
    private Integer saleFlagPlace;
    private Integer saleFlagBracketQuinella;
    private Integer saleFlagQuinella;
    private Integer saleFlagQuinellaPlace;
    private Integer saleFlagExacta;
    private Integer saleFlagTrio;
    private Integer placeCashKey;

    // 返還
    private List<Integer> restoreHorseNoItems;
    private List<Integer> restoreBracketItems;
    private List<Integer> restoreSameBracketItems;

    /**
     * 馬券種ごとの票数
     */
    private List<Vote> voteCountWins;
    private List<Vote> voteCountPlaces;
    private List<Vote> voteCountBracketQuinellas;
    private List<Vote> voteCountQuinellas;
    private List<Vote> voteCountQuinellaPlaces;
    private List<Vote> voteCountExactas;
    private List<Vote> voteCountTrios;

    /**
     * 取得する初期値スペース => null に変換される。
     */
    private Long voteCountTotalWin;
    private Long voteCountTotalPlace;
    private Long voteCountTotalBracketQuinella;
    private Long voteCountTotalQuinella;
    private Long voteCountTotalQuinellaPlace;
    private Long voteCountTotalExacta;
    private Long voteCountTotalTrio;

    /**
     * 取得する初期値スペース => null に変換される。
     */
    private Long restoreVoteCountTotalWin;
    private Long restoreVoteCountTotalPlace;
    private Long restoreVoteCountTotalBracketQuinella;
    private Long restoreVoteCountTotalQuinella;
    private Long restoreVoteCountTotalQuinellaPlace;
    private Long restoreVoteCountTotalExacta;
    private Long restoreVoteCountTotalTrio;


    @Data
    private static class Vote {

        private String horseNo;

        /**
         * ALL 0   :発売前取消し or 発売票数なし => null に変換される。
         * スペース :登録なし                   => null に変換される。
         */
        private Long voteCount;

        /**
         * スペース :登録なし   => null に変換される。
         * '---'   :発売前取消 => null に変換される。
         * '***'   :発売後取消 => null に変換される。
         */
        private Integer betRank;
    }

}
