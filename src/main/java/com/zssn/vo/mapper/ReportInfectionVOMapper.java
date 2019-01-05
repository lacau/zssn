package com.zssn.vo.mapper;

import com.zssn.model.entity.InfectionLog;
import com.zssn.model.entity.Survivor;
import com.zssn.vo.ReportInfectionVO;
import org.springframework.stereotype.Component;

@Component
public class ReportInfectionVOMapper extends VOMapper<InfectionLog, ReportInfectionVO> {

    public InfectionLog fromVO(ReportInfectionVO source) {
        return InfectionLog.builder()
            .reporter(Survivor.builder().id(source.getReporterId()).build())
            .reported(Survivor.builder().id(source.getReportedId()).build())
            .build();
    }
}
