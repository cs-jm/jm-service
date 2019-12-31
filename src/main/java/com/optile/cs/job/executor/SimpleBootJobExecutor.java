package com.optile.cs.job.executor;

import com.optile.cs.job.error.JobProcessingException;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.util.JobMessageCode;

import java.io.IOException;

public class SimpleBootJobExecutor extends JobExecutor {
    @Override
    public void execute(Job job) throws JobProcessingException {
        try {
            if (Runtime
                    .getRuntime()
                    .exec(String.join(" ", "java", job.getEnvironmentString(), "-jar", job.getFileLocation(), job.getId(), job.getParameters()))
                    .waitFor() != 0)
                throw new JobProcessingException(job.getId(), JobMessageCode.MESSAGE_002);
        } catch (InterruptedException | IOException exception) {
            throw new JobProcessingException(job.getId(), JobMessageCode.MESSAGE_002, exception);
        }
    }
}
