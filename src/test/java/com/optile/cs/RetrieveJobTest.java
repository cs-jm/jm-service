package com.optile.cs;

import com.optile.cs.helper.TestHelper;
import com.optile.cs.job.model.Job;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
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

    @BeforeEach
    public void setup(){

        webTestClient.post()
                     .uri("api/job")
                     .body(BodyInserters.fromMultipartData(testHelper.submitJobRequestData().build()))
                     .exchange()
                     .expectStatus()
                     .isCreated()
                     .expectHeader()
                     .value("location", location -> completableFuture.complete(location));
    }

    @Test
    public void testRetrieveJob() {
        try {
            webTestClient.get()
                         .uri(completableFuture.get())
                         .exchange()
                         .expectStatus()
                         .isOk()
                         .expectBody(Job.class);

        } catch (InterruptedException interruptedException) {
            log.error(interruptedException.getMessage());
        } catch (ExecutionException executionException) {
            log.error(executionException.getMessage());
        }
    }

    @Test
    public void testRetrieveAllJob() {
            webTestClient.get()
                         .uri("/api/job")
                         .exchange()
                         .expectStatus()
                         .isOk()
                         .expectBodyList(Job.class);
    }
}
