package com.zssn.service;

import com.zssn.model.entity.Inventory;
import com.zssn.model.entity.Survivor;
import com.zssn.model.enumeration.Resource;
import com.zssn.model.repository.SurvivorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SurvivorService {

    @Autowired
    private SurvivorRepository survivorRepository;

    @Transactional
    public Survivor create(Survivor survivor) {
        stackEqualResources(survivor);

        return survivorRepository.save(survivor);
    }

    @Transactional
    public void markAsInfected(Survivor survivor) {
        survivor.setInfected(true);
        survivorRepository.save(survivor);

        log.info("Survivor marked as infected. survivorId={}", survivor.getId());
    }

    public Survivor findById(Long id) {
        final Optional<Survivor> survivor = survivorRepository.findById(id);
        if (!survivor.isPresent()) {
            // TODO: thrown api exception
        }

        return survivor.get();
    }

    private void stackEqualResources(Survivor survivor) {
        final Map<String, Integer> collect = survivor.getInventory().stream()
            .collect(Collectors.groupingBy(i -> i.getResource().name(), Collectors.summingInt(i -> i.getQuantity())));

        final List<Inventory> inventory = new ArrayList<>();

        collect.forEach((key, value) -> inventory.add(Inventory.builder()
            .resource(Resource.valueOf(key))
            .quantity(value)
            .survivor(survivor)
            .build()));

        survivor.setInventory(inventory);
    }
}
