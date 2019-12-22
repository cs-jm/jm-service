package com.optile.cs.job;

import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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

    public Job findByJobId(String id) {
        return this
                .mongoTemplate
                .find(Query.query(Criteria.where("job.id").is(id)), JobDocument.class)
                .stream()
                .map(jobDocument -> jobDocument.getJob())
                .findFirst()
                .get();
    }
}
