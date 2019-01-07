package com.zssn.vo.mapper;

import com.zssn.model.entity.Inventory;
import com.zssn.vo.ResourceVO;
import org.springframework.stereotype.Component;

@Component
public class InventoryVOMapper extends VOMapper<Inventory, ResourceVO> {

    public Inventory fromVO(ResourceVO source) {
        return Inventory.builder()
            .quantity(source.getQuantity())
            .resource(source.getResource())
            .build();
    }

    @Override
    public ResourceVO toVO(Inventory source) {
        return ResourceVO.builder()
            .resource(source.getResource())
            .quantity(source.getQuantity())
            .build();
    }
}
