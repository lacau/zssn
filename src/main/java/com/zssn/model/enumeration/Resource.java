package com.zssn.model.enumeration;

import lombok.Getter;

@Getter
public enum Resource {

    AMMUNITION(1),
    MEDICATION(2),
    FOOD(3),
    WATER(4);

    int points;

    Resource(int points) {
        this.points = points;
    }
}
