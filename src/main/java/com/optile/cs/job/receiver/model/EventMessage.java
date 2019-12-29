package com.optile.cs.job.receiver.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventMessage implements Serializable {
    private String jobId;
    private String message;
}

