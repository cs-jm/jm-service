package com.optile.cs.job.executor;

import com.optile.cs.job.JobRepository;
import com.optile.cs.job.error.JobProcessingException;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobStatus;
import com.optile.cs.job.util.MessageCode;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class JobExecutor implements org.quartz.Job {
    protected Logger logger = LoggerFactory.getLogger(JobExecutor.class);

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

        this.updateJobStatus(job.getId(), JobStatus.RUNNING);

        try {
            this.execute(job);
            this.updateJobStatus(job.getId(), JobStatus.SUCCESS);

            logger.info(job.getId(), MessageCode.SUCCESSFUL_EXECUTION);
        } catch (JobProcessingException jobProcessingException) {
            logger.error(jobProcessingException.getJobId(), jobProcessingException.getMessageCode());
            logger.debug(jobProcessingException.getJobId(), jobProcessingException.getMessage());

            this.updateJobStatus(job.getId(), JobStatus.FAILED);
        }
    }
}
