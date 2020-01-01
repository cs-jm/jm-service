package com.optile.cs.job;

import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobDocument;
import com.optile.cs.job.model.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JobRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public String create(Job job) {
        return this.mongoTemplate
                .insert(new JobDocument(job))
                .getJob()
                .getId();
    }

    public void updateJobStatus(String id, JobStatus jobStatus) {
        this.mongoTemplate
                .findAndModify(
                        Query.query(Criteria.where("job.id").is(id)),
                        Update.update("job.status", jobStatus),
                        JobDocument.class);
    }

    public Job findByJobId(String id) {
        Job job = null;

        Optional<Job> optionalJob = this.mongoTemplate
                .find(Query.query(Criteria.where("job.id").is(id)), JobDocument.class)
                .stream()
                .map(JobDocument::getJob)
                .findFirst();

        if (optionalJob.isPresent())
            job = optionalJob.get();

        return job;
    }

    public List<Job> findAllJobs() {
        return this.mongoTemplate
                .findAll(JobDocument.class)
                .stream()
                .map(JobDocument::getJob)
                .collect(Collectors.toList());
    }
}
