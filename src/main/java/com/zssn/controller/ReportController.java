package com.zssn.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zssn.service.ReportService;
import com.zssn.vo.ReportBaseVO;
import com.zssn.vo.ResourceAvgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping(path = "/survivor/infected")
    public ReportBaseVO getInfectedSurvivorPercentage() {
        return new ReportBaseVO(reportService.reportInfected()) {

            @Override
            @JsonProperty("infected_survivor")
            public String getValue() {
                return super.getValue();
            }
        };
    }

    @GetMapping(path = "/survivor/non-infected")
    public ReportBaseVO getNonInfectedSurvivorPercentage() {
        return new ReportBaseVO(reportService.reportNonInfected()) {

            @Override
            @JsonProperty("non_infected_survivor")
            public String getValue() {
                return super.getValue();
            }
        };
    }

    @GetMapping(path = "/survivor/resource-avg")
    public ResourceAvgVO getAvegareResourceBySurvivor() {
        return reportService.reportResourceBySurvivor();
    }

    @GetMapping(path = "/survivor/points-lost")
    public ReportBaseVO getPointsLostByAllInfected() {
        return new ReportBaseVO(reportService.reportPointsLostByAllInfected()) {

            @Override
            @JsonProperty("points_lost_by_all_infected")
            public String getValue() {
                return super.getValue();
            }
        };
    }
}
