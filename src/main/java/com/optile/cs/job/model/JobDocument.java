package com.optile.cs.job.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Job")
public class JobDocument {
    @Id
    private String id;
    private Job job;

    public JobDocument(Job job) {
        this.job = job;
    }
}
