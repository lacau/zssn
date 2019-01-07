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
        Integer infected = survivorRepository.countByInfectedTrue();
        Integer health = survivorRepository.countByInfectedFalse();

        infected = infected != null ? infected : 0;
        health = health != null ? health : 0;

        final Integer total = infected + health;
        final Double percentage = calculatePercentage(infected, total);

        return String.format("%.2f%%", percentage);
    }

    public String reportNonInfected() {
        Integer infected = survivorRepository.countByInfectedTrue();
        Integer health = survivorRepository.countByInfectedFalse();

        infected = infected != null ? infected : 0;
        health = health != null ? health : 0;

        final Integer total = infected + health;
        final Double percentage = calculatePercentage(health, total);

        return String.format("%.2f%%", percentage);
    }

    public ResourceAvgVO reportResourceBySurvivor() {
        Integer survivorCount = survivorRepository.countByInfectedFalse();
        final List<ResourceCountQueryResult> resourceCountList = inventoryRepository.sumQuantityByResource();

        survivorCount = survivorCount != null ? survivorCount : 0;

        float avgAmmo = getAverageQuantityByResource(resourceCountList, Resource.AMMUNITION, survivorCount);
        float avgMedi = getAverageQuantityByResource(resourceCountList, Resource.MEDICATION, survivorCount);
        float avgWate = getAverageQuantityByResource(resourceCountList, Resource.WATER, survivorCount);
        float avgFood = getAverageQuantityByResource(resourceCountList, Resource.FOOD, survivorCount);

        return new ResourceAvgVO(round(avgAmmo), round(avgMedi), round(avgWate), round(avgFood));
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

    private float getAverageQuantityByResource(List<ResourceCountQueryResult> results, Resource resource, int survivorCount) {
        if (!CollectionUtils.isEmpty(results)) {
            for (ResourceCountQueryResult r : results) {
                if (r.getResource() == resource) {
                    return r.getQuantity() != null ? r.getQuantity().floatValue() / survivorCount : 0;
                }
            }
        }

        return 0;
    }

    private float round(float number) {
        return new BigDecimal(number).setScale(2, BigDecimal.ROUND_UP).floatValue();
    }

    private Double calculatePercentage(Integer value, Integer total) {
        return Double.valueOf(value) / total * 100;
    }
}
