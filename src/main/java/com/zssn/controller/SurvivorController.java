package com.zssn.controller;

import com.zssn.model.entity.Survivor;
import com.zssn.service.InfectionService;
import com.zssn.service.SurvivorService;
import com.zssn.vo.ReportInfectionVO;
import com.zssn.vo.SurvivorVO;
import com.zssn.vo.mapper.ReportInfectionVOMapper;
import com.zssn.vo.mapper.SurvivorVOMapper;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/survivor")
public class SurvivorController {

    @Autowired
    private SurvivorService survivorService;

    @Autowired
    private InfectionService infectionService;

    @Autowired
    private SurvivorVOMapper survivorVOMapper;

    @Autowired
    private ReportInfectionVOMapper reportInfectionVOMapper;

    @PostMapping
    public Survivor create(@RequestBody @Valid SurvivorVO request) {
        return survivorService.create(survivorVOMapper.fromVO(request));
    }

    @PostMapping(path = "/infected")
    public void reportInfection(@RequestBody @Valid ReportInfectionVO request) {
        infectionService.reportInfection(reportInfectionVOMapper.fromVO(request));
    }

    @GetMapping(path = "/{id}")
    public Survivor getById(@PathVariable("id") Long id) {
        return survivorService.findById(id);
    }
}
