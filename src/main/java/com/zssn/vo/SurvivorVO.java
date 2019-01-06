package com.zssn.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zssn.model.enumeration.Gender;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SurvivorVO {

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Min(1)
    @Max(11111111) // max value for 8 bits tinyint database type
    @JsonProperty("age")
    private Integer age;

    @NotNull
    @JsonProperty("gender")
    private Gender gender;

    @NotNull
    @Valid
    @JsonProperty("last_location")
    private LocationVO locationVO;

    @NotNull
    @Valid
    @JsonProperty("inventory")
    private List<ResourceVO> inventory;
}
