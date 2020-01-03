package com.optile.cs.job.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSchedule {
    @ApiModelProperty(notes = "Type of execution")
    private JobExecutionType executionType;
    @ApiModelProperty(notes = "Scheduled date and time")
    private Date schedule;
}
