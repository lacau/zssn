package com.zssn.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationVO {

    @NotNull
    @Digits(integer = 2, fraction = 6)
    @JsonProperty("latitude")
    private BigDecimal latitude;

    @NotNull
    @Digits(integer = 3, fraction = 6)
    @JsonProperty("longitude")
    private BigDecimal longitude;
}
