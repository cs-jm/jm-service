package com.optile.cs.job.error;

import com.optile.cs.job.util.MessageCode;
import lombok.Getter;

@Getter
public class JobProcessingException extends Exception {
    private String jobId;
    private MessageCode messageCode;

    public JobProcessingException(String jobId, MessageCode messageCode, Exception exception) {
        super(exception);
        this.jobId = jobId;
        this.messageCode = messageCode;
    }

    public JobProcessingException(String jobId, MessageCode messageCode) {
        this.jobId = jobId;
        this.messageCode = messageCode;
    }
}
