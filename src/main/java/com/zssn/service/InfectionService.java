package com.zssn.service;

import com.zssn.exceptions.ConflictApiException;
import com.zssn.model.entity.InfectionLog;
import com.zssn.model.entity.Survivor;
import com.zssn.model.repository.InfectionLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InfectionService {

    @Autowired
    private SurvivorService survivorService;

    @Autowired
    private InfectionLogRepository infectionLogRepository;

    @Value("${infection.hazard.cap:3}")
    private int infectionHazardCap;

    @Transactional
    public void reportInfection(InfectionLog infectionLog) {
        final Survivor reporter = survivorService.findById(infectionLog.getReporter().getId());
        final Survivor reported = survivorService.findById(infectionLog.getReported().getId());

        if (infectionLogRepository.isAlteradyReported(reporter.getId(), reported.getId())) {
            log.warn("Survivor already reported. reporterId={}, reportedId={}", reporter.getId(), reported.getId());
            throw new ConflictApiException("conflict.survivor.already.reported");
        }

        log.info("Survivor reported as infected. reporterId={}, reportedId={}", reporter.getId(), reported.getId());

        final InfectionLog infectionLogDB = InfectionLog.builder()
            .reporter(reporter)
            .reported(reported)
            .build();

        infectionLogRepository.save(infectionLogDB);

        if (!reported.isInfected()) {
            final int infectionCount = infectionLogRepository.countByReportedId(reported.getId());
            if (infectionCount >= infectionHazardCap) {
                log.info("Survivor reach infection limit. survivorId={}", reported.getId());
                survivorService.markAsInfected(reported);
            }
        }
    }
}
