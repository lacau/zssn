package com.zssn.vo.mapper;

import com.zssn.model.entity.Inventory;
import com.zssn.model.entity.Location;
import com.zssn.model.entity.Survivor;
import com.zssn.vo.LocationVO;
import com.zssn.vo.ResourceVO;
import com.zssn.vo.SurvivorVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SurvivorVOMapper extends VOMapper<Survivor, SurvivorVO> {

    @Autowired
    private InventoryVOMapper inventoryVOMapper;

    @Autowired
    private LocationVOMapper locationVOMapper;

    public Survivor fromVO(SurvivorVO source) {
        final List<Inventory> inventory = inventoryVOMapper.fromVO(source.getInventory());
        final Location location = locationVOMapper.fromVO(source.getLocationVO());

        return Survivor.builder()
            .name(source.getName())
            .age(source.getAge())
            .gender(source.getGender())
            .inventory(inventory)
            .location(location)
            .build();
    }

    @Override
    public SurvivorVO toVO(Survivor source) {
        final List<ResourceVO> inventory = inventoryVOMapper.toVO(source.getInventory());
        final LocationVO location = locationVOMapper.toVO(source.getLocation());

        return SurvivorVO.builder()
            .id(source.getId())
            .name(source.getName())
            .age(source.getAge())
            .gender(source.getGender())
            .infected(source.isInfected())
            .inventory(inventory)
            .locationVO(location)
            .build();
    }
}
