package com.zssn.util;

import com.zssn.model.entity.Inventory;
import com.zssn.model.entity.Survivor;
import com.zssn.model.enumeration.Resource;
import com.zssn.vo.ResourceVO;
import com.zssn.vo.TradeSurvivorVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtils {

    public void stackEqualResources(Survivor survivor) {
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

    public void stackEqualResources(TradeSurvivorVO survivor) {
        final Map<String, Integer> collect = survivor.getResources().stream()
            .collect(Collectors.groupingBy(i -> i.getResource().name(), Collectors.summingInt(i -> i.getQuantity())));

        final List<ResourceVO> inventory = new ArrayList<>();

        collect.forEach((key, value) -> inventory.add(ResourceVO.builder()
            .resource(Resource.valueOf(key))
            .quantity(value)
            .build()));

        survivor.setResources(inventory);
    }
}
