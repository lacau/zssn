package com.zssn.vo.mapper;

import com.zssn.model.entity.Location;
import com.zssn.vo.LocationVO;
import org.springframework.stereotype.Component;

@Component
public class LocationVOMapper extends VOMapper<Location, LocationVO> {

    public Location fromVO(LocationVO source) {
        return Location.builder()
            .latitude(source.getLatitude())
            .longitude(source.getLongitude())
            .build();
    }

    @Override
    public LocationVO toVO(Location source) {
        return LocationVO.builder()
            .latitude(source.getLatitude())
            .longitude(source.getLongitude())
            .build();
    }
}
