package com.optile.cs;

import com.optile.cs.job.model.JobExecutionType;
import com.optile.cs.job.model.JobType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubmitJobTest {

    @Autowired
    private WebTestClient webTestClient;

    private byte[] getFile(){
        try {
            return Files.readAllBytes(new ClassPathResource("jm-job-logger-1.0.jar").getFile().toPath());
        } catch (IOException e) {
            return null;
        }
    }

    @Test
    public void testSubmitJob() {

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

        multipartBodyBuilder
                .part("file", getFile())
               .header("Content-Disposition", "form-data; name=file; filename=jm-job-logger-1.0.jar");
        multipartBodyBuilder.part("jobType", JobType.SPRING_BOOT_JAR.toString());
        multipartBodyBuilder.part("executionType", JobExecutionType.IMMEDIATE.toString());

        webTestClient.post()
                .uri("api/job")
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus()
                .isCreated();
    }

}
