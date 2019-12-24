package com.optile.cs.job;

import com.optile.cs.error.AppErrorCode;
import com.optile.cs.error.AppException;
import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobStatus;
import com.optile.cs.job.model.JobType;
import com.optile.cs.job.service.SchedulerService;
import com.optile.cs.job.service.StorageService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private JobRepository jobRepository;

    public String submitJob(MultipartFile file, JobType jobType) {
        String jobId = UUID.randomUUID().toString();
        String jobFileLocation = storageService.saveFile(file, jobId);

        Job job = Job
                .builder()
                .id(jobId)
                .fileLocation(jobFileLocation)
                .status(JobStatus.QUEUED)
                .type(jobType)
                .build();

        try {
            schedulerService.scheduleJob(job);
        }catch (SchedulerException schedulerException){
            throw new AppException(AppErrorCode.ERROR_SCHEDULE_JOB);
        }

        return jobRepository.create(job);


    }

    public Job retrieveJob(String id) {
        return jobRepository.findByJobId(id);
    }
}
