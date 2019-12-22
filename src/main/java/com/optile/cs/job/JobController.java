package com.optile.cs.job;

import com.optile.cs.job.model.JobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("job")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping()
    public ResponseEntity<?> submitJob(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobType") JobType jobType) {

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(jobService.submitJob(jobType))
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveJob(@PathVariable("id") String id) {
        return ResponseEntity.ok(jobService.retrieveJob(id));
    }

}
