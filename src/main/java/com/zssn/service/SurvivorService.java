package com.zssn.service;

import com.zssn.model.entity.Survivor;
import com.zssn.model.repository.SurvivorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurvivorService {

    @Autowired
    private SurvivorRepository survivorRepository;

    public Survivor create(Survivor survivor) {
        return survivorRepository.save(survivor);
    }

    public Survivor findById(Long id) {
        return survivorRepository.findById(id).get();
    }
}
