package com.zssn.model.repository;

import com.zssn.model.entity.Inventory;
import com.zssn.model.query.ResourceCountQueryResult;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    List<Inventory> findBySurvivorInfectedIsTrue();

    @Query(value = ResourceCountQueryResult.QUERY)
    List<ResourceCountQueryResult> sumQuantityByResource();
}
