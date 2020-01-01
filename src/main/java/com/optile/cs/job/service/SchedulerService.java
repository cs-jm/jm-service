package com.optile.cs.job.service;

import com.optile.cs.error.AppResponseException;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobType;
import com.optile.cs.job.util.JobResponseErrorCode;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SchedulerService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private Map<JobType, Class> executors;

    public void scheduleJob(Job job) {
        JobDetail jobDetail = this.getJobDetail(job);

        try {
            schedulerFactoryBean
                    .getScheduler()
                    .scheduleJob(jobDetail, getTrigger(jobDetail, job));
        } catch (SchedulerException schedulerException) {
            throw new AppResponseException(JobResponseErrorCode.RESPONSE_ERROR_003);
        }
    }

    private Trigger getTrigger(JobDetail jobDetail, Job job) {
        TriggerBuilder triggerBuilder = TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(job.getId());

        if (job.getPriority() != null)
            triggerBuilder.withPriority(job.getPriority());

        switch (job.getSchedule().getExecutionType()) {
            case SCHEDULED:
                triggerBuilder.startAt(job.getSchedule().getSchedule());
                break;
            case IMMEDIATE:
            default:
                triggerBuilder.startNow();
                break;
        }

        return triggerBuilder.build();
    }

    private JobDetail getJobDetail(Job job) {
        JobDetail jobDetail = JobBuilder
                .newJob()
                .ofType(executors.get(job.getType()))
                .storeDurably()
                .withIdentity(job.getId())
                .build();

        jobDetail
                .getJobDataMap()
                .put("data", job);

        return jobDetail;
    }
}
