package com.optile.cs.job.executor;

import com.optile.cs.job.error.JobProcessingException;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.util.MessageCode;

import java.io.IOException;

public class SimpleJarJobExecutor extends JobExecutor {
    @Override
    public void execute(Job job) throws JobProcessingException {
        try {
            if (new ProcessBuilder().command("java", "-jar", job.getFileLocation()).start().waitFor() != 0)
                throw new JobProcessingException(job.getId(), MessageCode.FAILED_EXECUTION);
        } catch (InterruptedException | IOException exception) {
            throw new JobProcessingException(job.getId(), MessageCode.FAILED_EXECUTION, exception);
        }
    }
}
