package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.feed.application.repository.impl.JvStoredRaceRefundRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RaceRefundService {

    private final JvStoredRaceRefundRepository refundRepository;


    public List<RaceRefund> findAllOnThisWeek(ZonedDateTime dateTime) {
        return refundRepository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<RaceRefund> findAllOnStandard(ZonedDateTime dateTime) {
        return refundRepository.findAll(dateTime, Option.STANDARD);
    }

    public List<RaceRefund> findAllOnSetUpWithoutDialog(ZonedDateTime dateTime) {
        return refundRepository.findAll(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }


}
