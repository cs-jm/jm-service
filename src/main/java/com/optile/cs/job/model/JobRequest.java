package com.optile.cs.job.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor
public class JobRequest {
    @NotNull
    private JobType jobType;
}
