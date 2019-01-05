package com.zssn.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportInfectionVO {

    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
    @JsonProperty("reporter_id")
    private Long reporterId;

    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE) // max value for 8 bits tinyint database type
    @JsonProperty("reported_id")
    private Long reportedId;
}
