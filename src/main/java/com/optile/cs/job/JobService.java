package com.optile.cs.job;

import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobStatus;
import com.optile.cs.job.model.JobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public String submitJob(JobType jobType) {
        return jobRepository.create(Job
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .status(JobStatus.QUEUED)
                                            .type(jobType)
                                            .build());

    }

    public Job retrieveJob(String id) {
        return jobRepository.findByJobId(id);
    }
}
