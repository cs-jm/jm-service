package com.optile.cs.job.service;

import com.optile.cs.job.executor.SimpleJarExecutor;
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

    public void scheduleJob(Job job) throws SchedulerException {
        JobDetail jobDetail = this.getJob(job);

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(job.getId())
                .startNow()
                .build();

        schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
    }

    private JobDetail getJob(Job job){
        return JobBuilder
                .newJob()
                .ofType(executors.get(job.getType()))
                .storeDurably()
                .withIdentity(job.getId())
                .build();
    }
}
