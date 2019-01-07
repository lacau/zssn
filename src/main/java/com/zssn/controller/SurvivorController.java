package com.zssn.controller;

import com.zssn.model.entity.Survivor;
import com.zssn.service.InfectionService;
import com.zssn.service.SurvivorService;
import com.zssn.vo.LocationVO;
import com.zssn.vo.ReportInfectionVO;
import com.zssn.vo.SurvivorVO;
import com.zssn.vo.mapper.LocationVOMapper;
import com.zssn.vo.mapper.ReportInfectionVOMapper;
import com.zssn.vo.mapper.SurvivorVOMapper;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private LocationVOMapper locationVOMapper;

    @PostMapping
    public SurvivorVO create(@RequestBody @Valid SurvivorVO request) {
        final Survivor survivor = survivorService.create(survivorVOMapper.fromVO(request));

        return survivorVOMapper.toVO(survivor);
    }

    @PutMapping("/{id}/location")
    public SurvivorVO updateLocation(@PathVariable("id") Long survivorId, @RequestBody @Valid LocationVO request) {
        final Survivor survivor = survivorService.updateLocation(survivorId, locationVOMapper.fromVO(request));

        return survivorVOMapper.toVO(survivor);
    }

    @PostMapping(path = "/infected")
    public void reportInfection(@RequestBody @Valid ReportInfectionVO request) {
        infectionService.reportInfection(reportInfectionVOMapper.fromVO(request));
    }

    @GetMapping(path = "/{id}")
    public SurvivorVO getById(@PathVariable("id") Long id) {
        final Survivor survivor = survivorService.findById(id);

        return survivorVOMapper.toVO(survivor);
    }
}
