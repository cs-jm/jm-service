package com.optile.cs.job.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCode {
    SUCCESSFUL_EXECUTION("Job execution successfully"),
    FAILED_EXECUTION("Job execution failed");

    private String message;
}
