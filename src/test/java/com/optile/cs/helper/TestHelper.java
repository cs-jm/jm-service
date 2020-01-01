package com.optile.cs.helper;

import com.optile.cs.job.model.JobExecutionType;
import com.optile.cs.job.model.JobType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class TestHelper {
    private byte[] getFile() {
        try {
            return Files.readAllBytes(new ClassPathResource("jm-job-logger-1.0.jar").getFile().toPath());
        } catch (IOException e) {
            return null;
        }
    }

    public MultipartBodyBuilder submitJobRequestData(){
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

        multipartBodyBuilder
                .part("file", getFile())
                .header("Content-Disposition", "form-data; name=file; filename=jm-job-logger-1.0.jar");
        multipartBodyBuilder.part("jobType", JobType.SPRING_BOOT_JAR.toString());
        multipartBodyBuilder.part("executionType", JobExecutionType.IMMEDIATE.toString());

        return multipartBodyBuilder;
    }

    public MultipartBodyBuilder submitJobRequestData_MissingJobFile(){
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

        multipartBodyBuilder.part("jobType", JobType.SPRING_BOOT_JAR.toString());
        multipartBodyBuilder.part("executionType", JobExecutionType.IMMEDIATE.toString());

        return multipartBodyBuilder;
    }

}
