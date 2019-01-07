package com.zssn.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeSurvivorVO {

    private Long id;

    @NotNull
    @Valid
    @JsonProperty("resources")
    private List<ResourceVO> resources;
}
