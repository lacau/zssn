package com.zssn.vo.mapper;

import com.zssn.model.entity.Survivor;
import com.zssn.vo.SurvivorVO;
import org.springframework.stereotype.Component;

@Component
public class SurvivorVOMapper extends VOMapper<Survivor, SurvivorVO> {

    public Survivor fromVO(SurvivorVO source) {
        return Survivor.builder()
            .name(source.getName())
            .age(source.getAge())
            .gender(source.getGender())
            .build();
    }
}
