package com.optile.cs.job.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Job {
    private String id;
    private JobType type;
    private JobStatus status;
}
