package com.optile.cs.job.executor;

import com.optile.cs.job.JobRepository;
import com.optile.cs.job.error.JobProcessingException;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobStatus;
import com.optile.cs.job.util.JobMessageCode;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public abstract class JobExecutor implements org.quartz.Job {
    @Autowired
    private JobRepository jobRepository;

    private void updateJobStatus(String id, JobStatus jobStatus) {
        jobRepository.updateJobStatus(id, jobStatus);
    }

    public abstract void execute(Job job) throws JobProcessingException;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Job job = (Job) jobExecutionContext
                .getJobDetail()
                .getJobDataMap()
                .get("data");

        try {
            this.execute(job);
            log.info(String.format("%s, %s", job.getId(), JobMessageCode.MESSAGE_001.getMessage()));

        } catch (JobProcessingException jobProcessingException) {
            log.error(String.format("%s, %s", jobProcessingException.getJobId(), jobProcessingException.getJobMessageCode().getMessage()));
            log.debug(String.format("%s, %s", jobProcessingException.getJobId(), jobProcessingException.getMessage()));

            this.updateJobStatus(job.getId(), JobStatus.FAILED);
        }
    }
}
