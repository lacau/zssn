package com.zssn.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TradeVO {

    @NotNull
    @Valid
    @JsonProperty("giver")
    private TradeSurvivorVO giver;

    @NotNull
    @Valid
    @JsonProperty("receiver")
    private TradeSurvivorVO receiver;
}
