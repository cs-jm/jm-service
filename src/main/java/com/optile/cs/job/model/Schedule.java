package com.optile.cs.job.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule {
    @ApiModelProperty(notes = "Type of execution")
    private ExecutionType executionType;
    @ApiModelProperty(notes = "Scheduled date and time")
    private Date schedule;
}
