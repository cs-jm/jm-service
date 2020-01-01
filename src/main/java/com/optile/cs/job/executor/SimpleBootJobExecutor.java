package com.optile.cs.job.executor;

import com.optile.cs.job.error.JobProcessingException;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.util.JobMessageCode;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleBootJobExecutor extends JobExecutor {
    @Override
    public void execute(Job job) throws JobProcessingException {
        try {
            String executionString = Stream.of("java", job.getEnvironmentString(), "-jar", job.getFileLocation(), job.getId(), job.getParameters())
                                           .filter(token -> token != null && !token.isEmpty())
                                           .collect(Collectors.joining(" "));

            if (Runtime
                    .getRuntime()
                    .exec(executionString)
                    .waitFor() != 0)
                throw new JobProcessingException(job.getId(), JobMessageCode.MESSAGE_002);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        } catch (IOException exception) {
            throw new JobProcessingException(job.getId(), JobMessageCode.MESSAGE_002, exception);
        }
    }
}
