package com.zssn.service;

import com.zssn.exceptions.NotFoundApiException;
import com.zssn.model.entity.Location;
import com.zssn.model.entity.Survivor;
import com.zssn.model.repository.SurvivorRepository;
import com.zssn.util.ResourceUtils;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SurvivorService {

    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private ResourceUtils resourceUtils;

    @Transactional
    public Survivor create(Survivor survivor) {
        resourceUtils.stackEqualResources(survivor);
        survivor.getLocation().setSurvivor(survivor);

        return survivorRepository.save(survivor);
    }

    @Transactional
    public void markAsInfected(Survivor survivor) {
        survivor.setInfected(true);
        survivorRepository.save(survivor);

        log.info("Survivor marked as infected. survivorId={}", survivor.getId());
    }

    @Transactional
    public Survivor updateLocation(Long survivorId, Location location) {
        final Survivor survivor = findById(survivorId);
        survivor.getLocation().setLatitude(location.getLatitude());
        survivor.getLocation().setLongitude(location.getLongitude());

        return survivorRepository.save(survivor);
    }

    public Survivor findById(Long id) {
        final Optional<Survivor> survivor = survivorRepository.findById(id);
        if (!survivor.isPresent()) {
            log.warn("Attempt to find invalid survivor. survivorId={}", id);
            throw new NotFoundApiException("notfound.survivor.does.not.exists");
        }

        return survivor.get();
    }
}
