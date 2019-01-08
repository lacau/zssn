package com.zssn.service;

import com.zssn.exceptions.ConflictApiException;
import com.zssn.exceptions.UnprocessableEntityApiException;
import com.zssn.model.entity.InfectionLog;
import com.zssn.model.entity.Survivor;
import com.zssn.model.repository.InfectionLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class InfectionServiceTest {

    @InjectMocks
    private InfectionService infectionService;

    @Mock
    private SurvivorService survivorService;

    @Mock
    private InfectionLogRepository infectionLogRepository;

    @Test(expected = UnprocessableEntityApiException.class)
    public void testReportInfectionWhenReporterAndReportedAreEqual() {
        final InfectionLog infectionLog = createInfectionLog(1L, 1L);

        infectionService.reportInfection(infectionLog);
    }

    @Test(expected = ConflictApiException.class)
    public void testReportInfectionWhenAlreadyReported() {
        final InfectionLog infectionLog = createInfectionLog(1L, 2L);
        Mockito.when(survivorService.findById(infectionLog.getReporter().getId())).thenReturn(infectionLog.getReporter());
        Mockito.when(survivorService.findById(infectionLog.getReported().getId())).thenReturn(infectionLog.getReported());
        Mockito.when(infectionLogRepository.isAlteradyReported(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

        infectionService.reportInfection(infectionLog);
    }

    @Test
    public void testReportInfectionWhenNotInfected() {
        final InfectionLog infectionLog = createInfectionLog(1L, 2L);
        Mockito.when(survivorService.findById(infectionLog.getReporter().getId())).thenReturn(infectionLog.getReporter());
        Mockito.when(survivorService.findById(infectionLog.getReported().getId())).thenReturn(infectionLog.getReported());
        Mockito.when(infectionLogRepository.isAlteradyReported(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
        Mockito.when(infectionLogRepository.countByReportedId(Mockito.anyLong())).thenReturn(0);
        ReflectionTestUtils.setField(infectionService, "infectionHazardCap", 3);

        infectionService.reportInfection(infectionLog);
        Mockito.verify(infectionLogRepository, Mockito.times(1)).save(Mockito.any(InfectionLog.class));
        Mockito.verify(survivorService, Mockito.times(0)).markAsInfected(Mockito.any(Survivor.class));
    }

    @Test
    public void testReportInfectionWhenShouldMarkAsInfected() {
        final InfectionLog infectionLog = createInfectionLog(1L, 2L);
        Mockito.when(survivorService.findById(infectionLog.getReporter().getId())).thenReturn(infectionLog.getReporter());
        Mockito.when(survivorService.findById(infectionLog.getReported().getId())).thenReturn(infectionLog.getReported());
        Mockito.when(infectionLogRepository.isAlteradyReported(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
        Mockito.when(infectionLogRepository.countByReportedId(Mockito.anyLong())).thenReturn(3);
        ReflectionTestUtils.setField(infectionService, "infectionHazardCap", 3);

        infectionService.reportInfection(infectionLog);
        Mockito.verify(infectionLogRepository, Mockito.times(1)).save(Mockito.any(InfectionLog.class));
        Mockito.verify(survivorService, Mockito.times(1)).markAsInfected(Mockito.any(Survivor.class));
    }

    private InfectionLog createInfectionLog(Long reporterId, Long reportedId) {
        return InfectionLog.builder()
            .reporter(Survivor.builder().id(reporterId).build())
            .reported(Survivor.builder().id(reportedId).build())
            .build();
    }
}
