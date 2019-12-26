package com.optile.cs.job.error;

import com.optile.cs.job.util.JobMessageCode;
import lombok.Getter;

@Getter
public class JobProcessingException extends Exception {
    private String jobId;
    private JobMessageCode jobMessageCode;

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
