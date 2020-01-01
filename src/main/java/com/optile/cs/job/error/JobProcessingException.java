package com.optile.cs.job.error;

import com.optile.cs.job.util.JobMessageCode;
import lombok.Getter;

@Getter
public class JobProcessingException extends Exception {
    private final String jobId;
    private final JobMessageCode jobMessageCode;

    public JobProcessingException(String jobId, JobMessageCode jobMessageCode, Exception exception) {
        super(exception);
        this.jobId = jobId;
        this.jobMessageCode = jobMessageCode;
    }

    public JobProcessingException(String jobId, JobMessageCode jobMessageCode) {
        this.jobId = jobId;
        this.jobMessageCode = jobMessageCode;
    }
}
