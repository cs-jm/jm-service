package com.optile.cs.job.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Job {
    @ApiModelProperty(notes = "The auto-generated job id")
    private String id;
    @ApiModelProperty(notes = "Type of job")
    private JobType type;
    @ApiModelProperty(notes = "Current status of job")
    private JobStatus status;
    @ApiModelProperty(notes = "location of job file")
    private String fileLocation;
    @ApiModelProperty(notes = "priority of job")
    private Integer priority;
    @ApiModelProperty(notes = "Schedule of job")
    private Schedule schedule;
}
