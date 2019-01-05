package com.zssn.vo.mapper;

import com.zssn.model.entity.Inventory;
import com.zssn.model.entity.Survivor;
import com.zssn.vo.SurvivorVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SurvivorVOMapper extends VOMapper<Survivor, SurvivorVO> {

    @Autowired
    private InventoryVOMapper inventoryVOMapper;

    public Survivor fromVO(SurvivorVO source) {
        final List<Inventory> inventory = inventoryVOMapper.fromVO(source.getInventory());

        return Survivor.builder()
            .name(source.getName())
            .age(source.getAge())
            .gender(source.getGender())
            .inventory(inventory)
            .build();
    }
}
