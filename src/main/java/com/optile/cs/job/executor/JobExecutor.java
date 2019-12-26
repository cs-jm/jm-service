package com.optile.cs.job.executor;

import com.optile.cs.job.JobRepository;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobStatus;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class JobExecutor implements org.quartz.Job {

    @Autowired
    private JobRepository jobRepository;

    private void start(String id) {
        jobRepository.updateJobStatus(id, JobStatus.RUNNUG);
    }

    public abstract void execute(Job job);

    private void success(String id) {
        jobRepository.updateJobStatus(id, JobStatus.SUCCESS);
    }

    private void failed(String id) {
        jobRepository.updateJobStatus(id, JobStatus.FAILED);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Job job = (Job) jobExecutionContext
                .getJobDetail()
                .getJobDataMap()
                .get("data");

        this.start(job.getId());

        try {
            this.execute(job);
            this.success(job.getId());
        } catch (Exception exception) {
            this.failed(job.getId());
        }
    }
}
