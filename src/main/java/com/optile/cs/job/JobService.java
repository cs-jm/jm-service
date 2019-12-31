package com.optile.cs.job;

import com.optile.cs.job.model.*;
import com.optile.cs.job.service.SchedulerService;
import com.optile.cs.store.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private JobRepository jobRepository;

    public String submitJob(MultipartFile file, JobType jobType, JobExecutionType executionType, Date date, Integer priority, String parameters, String environmentString) {
        String jobId = UUID.randomUUID().toString();

        Job job = Job
                .builder()
                .id(jobId)
                .fileLocation(
                        storageService.saveFile(file, jobId))
                .status(JobStatus.QUEUED)
                .type(jobType)
                .schedule(
                        new JobSchedule(executionType, date))
                .priority(priority)
                .parameters(parameters)
                .environmentString(environmentString)
                .build();

        schedulerService
                .scheduleJob(job);

        return jobRepository.create(job);
    }

    public Job retrieveJob(String id) {
        return jobRepository.findByJobId(id);
    }

    public List<Job> retrieveAllJobs() {
        return jobRepository.findAllJobs();
    }
}
