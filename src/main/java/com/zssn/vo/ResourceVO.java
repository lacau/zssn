package com.zssn.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zssn.model.enumeration.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResourceVO {

    @NotNull
    @JsonProperty("resource")
    private Resource resource;

    @NotNull
    @Min(1)
    @Max(11111111) // max value for 8 bits tinyint database type
    @JsonProperty("quantity")
    private Integer quantity;
}
