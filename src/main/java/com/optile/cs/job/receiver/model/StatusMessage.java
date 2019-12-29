package com.optile.cs.job.receiver.model;

import com.optile.cs.job.model.JobStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatusMessage implements Serializable {
    private String jobId;
    private JobStatus jobStatus;
}
