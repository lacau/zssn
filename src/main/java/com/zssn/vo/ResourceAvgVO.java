package com.zssn.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceAvgVO {

    @JsonProperty("avg_ammunition")
    private float avgAmmo;

    @JsonProperty("avg_medication")
    private float avgMedi;

    @JsonProperty("avg_water")
    private float avgWate;

    @JsonProperty("avg_food")
    private float avgFood;
}
