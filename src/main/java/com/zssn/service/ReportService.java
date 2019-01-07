package com.zssn.service;

import com.zssn.model.entity.Inventory;
import com.zssn.model.enumeration.Resource;
import com.zssn.model.query.ResourceCountQueryResult;
import com.zssn.model.repository.InventoryRepository;
import com.zssn.model.repository.SurvivorRepository;
import com.zssn.vo.ResourceAvgVO;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ReportService {

    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public String reportInfected() {
        final Integer infected = survivorRepository.countByInfectedTrue();
        final Integer health = survivorRepository.countByInfectedFalse();

        final Integer total = infected + health;
        final Double percentage = Double.valueOf(infected) / total * 100;

        return String.format("%.2f%%", percentage);
    }

    public String reportNonInfected() {
        final Integer infected = survivorRepository.countByInfectedTrue();
        final Integer health = survivorRepository.countByInfectedFalse();

        final Integer total = infected + health;
        final Double percentage = Double.valueOf(health) / total * 100;

        return String.format("%.2f%%", percentage);
    }

    public ResourceAvgVO reportResourceBySurvivor() {
        final int survivorCount = survivorRepository.countByInfectedFalse();
        final List<ResourceCountQueryResult> resourceCountList = inventoryRepository.sumQuantityByResource();

        float avgAmmo = getAverageQuantityByResource(resourceCountList, Resource.AMMUNITION, survivorCount);
        float avgMedi = getAverageQuantityByResource(resourceCountList, Resource.MEDICATION, survivorCount);
        float avgWate = getAverageQuantityByResource(resourceCountList, Resource.WATER, survivorCount);
        float avgFood = getAverageQuantityByResource(resourceCountList, Resource.FOOD, survivorCount);

        return new ResourceAvgVO(round(avgAmmo), round(avgMedi), round(avgWate), round(avgFood));
    }

    private float getAverageQuantityByResource(List<ResourceCountQueryResult> results, Resource resource, int survivorCount) {
        for (ResourceCountQueryResult r : results) {
            if (r.getResource() == resource) {
                return r.getQuantity() != null ? r.getQuantity().floatValue() / survivorCount : 0;
            }
        }

        return 0;
    }

    private float round(float number) {
        return new BigDecimal(number).setScale(2, BigDecimal.ROUND_UP).floatValue();
    }

    public String reportPointsLostByAllInfected() {
        final List<Inventory> inventories = inventoryRepository.findBySurvivorInfectedIsTrue();
        int pointsLost = 0;
        if (!CollectionUtils.isEmpty(inventories)) {
            for (Inventory inventory : inventories) {
                pointsLost += inventory.getQuantity() * inventory.getResource().getPoints();
            }
        }

        return String.valueOf(pointsLost);
    }
}
