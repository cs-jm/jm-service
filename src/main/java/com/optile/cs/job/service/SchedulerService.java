package com.optile.cs.job.service;

import com.optile.cs.error.AppErrorCode;
import com.optile.cs.error.AppException;
import com.optile.cs.job.model.ExecutionType;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobType;
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
            schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, getTrigger(jobDetail, job));
        } catch (SchedulerException schedulerException) {
            throw new AppException(AppErrorCode.ERROR_SCHEDULE_JOB);
        }
    }

    private Trigger getTrigger(JobDetail jobDetail, Job job) {
        TriggerBuilder triggerBuilder = TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(job.getId())
                .withPriority(job.getPriority());

        if (job.getSchedule().getExecutionType().equals(ExecutionType.IMMEDIATE))
            triggerBuilder.startNow();
        else
            triggerBuilder.startAt(job.getSchedule().getSchedule());

        return triggerBuilder.build();
    }

    private JobDetail getJobDetail(Job job) {
        return JobBuilder
                .newJob()
                .ofType(executors.get(job.getType()))
                .storeDurably()
                .withIdentity(job.getId())
                .build();
    }
}
