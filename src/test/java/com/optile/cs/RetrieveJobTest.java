package com.optile.cs;

import com.optile.cs.helper.TestHelper;
import com.optile.cs.job.model.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Awaitility;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RetrieveJobTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestHelper testHelper;

    private CompletableFuture<String> completableFuture = new CompletableFuture<String>();
    private Job job;

    @BeforeEach
    public void setup() throws ExecutionException, InterruptedException {
        webTestClient.post()
                     .uri("api/job")
                     .body(BodyInserters.fromMultipartData(testHelper.submitJobRequestData().build()))
                     .exchange()
                     .expectStatus()
                     .isCreated()
                     .expectHeader()
                     .value("location", location -> completableFuture.complete(location));


        String jobId = StringUtils
                .substringAfter(completableFuture.get(), "/api/job/");

        job = Job
                .builder()
                .id(jobId)
                .type(JobType.SPRING_BOOT_JAR)
                .status(JobStatus.SUCCESS)
                .fileLocation(".store/" + jobId + ".jar")
                .schedule(new JobSchedule(JobExecutionType.IMMEDIATE, null))
                .build();
    }

    @Test
    public void testRetrieveJob() throws ExecutionException, InterruptedException {
        Awaitility
                .await()
                .pollDelay(Duration.ofSeconds(5))
                .timeout(Duration.ofSeconds(10))
                .until(() -> webTestClient.get()
                                            .uri(completableFuture.get())
                                            .exchange()
                                            .expectStatus()
                                            .isOk()
                                            .expectBody(Job.class)
                                            .returnResult()
                                            .getResponseBody()
                        , CoreMatchers.equalTo(job));
    }

    @Test
    public void testRetrieveAllJob() {
        Awaitility
                .await()
                .pollDelay(Duration.ofSeconds(5))
                .timeout(Duration.ofSeconds(10))
                .until(() -> webTestClient.get()
                                          .uri("/api/job")
                                          .exchange()
                                          .expectStatus()
                                          .isOk()
                                          .expectBodyList(Job.class)
                                          .returnResult()
                                          .getResponseBody()
                        , CoreMatchers.hasItem(job));

    }
}
