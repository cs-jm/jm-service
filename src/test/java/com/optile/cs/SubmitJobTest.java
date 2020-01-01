package com.optile.cs;

import com.optile.cs.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubmitJobTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestHelper testHelper;

    @Test
    public void testSubmitJob() {

        webTestClient.post()
                     .uri("api/job")
                     .body(BodyInserters.fromMultipartData(testHelper.submitJobRequestData().build()))
                     .exchange()
                     .expectStatus()
                     .isCreated();
    }

    @Test
    public void testSubmitJob_MissingJobFile() {

        webTestClient.post()
                     .uri("api/job")
                     .body(BodyInserters.fromMultipartData(testHelper.submitJobRequestData_MissingJobFile().build()))
                     .exchange()
                     .expectStatus()
                     .isBadRequest();

    }

}
