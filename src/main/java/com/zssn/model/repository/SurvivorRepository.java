package com.zssn.model.repository;

import com.zssn.model.entity.Survivor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurvivorRepository extends CrudRepository<Survivor, Long> {

    Integer countByInfectedFalse();

    Integer countByInfectedTrue();
}
