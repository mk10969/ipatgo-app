package org.uma.daiwaScarlet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.daiwaScarlet.model.RaceRefund;
import org.uma.daiwaScarlet.repository.impl.JvStoredRaceRefundRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class RaceRefundService {

    private final JvStoredRaceRefundRepository refundRepository;

    @Autowired
    public RaceRefundService(JvStoredRaceRefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

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
