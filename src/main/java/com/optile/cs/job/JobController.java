package com.optile.cs.job;

import com.optile.cs.job.model.Job;
import com.optile.cs.job.model.JobExecutionType;
import com.optile.cs.job.model.JobType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("job")
@Api(value = "Job Management", tags = "Submit/Retrieve Job Operations")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping()
    @ApiOperation(value = "Submit a job to be executed")
    public ResponseEntity<?> submitJob(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobType") JobType jobType,
            @RequestParam(name = "priority", required = false) Integer priority,
            @RequestParam("executionType") JobExecutionType executionType,
            @RequestParam(name = "environmentString", required = false) String environmentString,
            @RequestParam(name = "schedule", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date,
            @RequestParam(name = "parameters", required = false) String parameters) {

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(jobService.submitJob(file, jobType, executionType, date, priority, parameters, environmentString))
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a job to by id", response = Job.class)
    public ResponseEntity<?> retrieveJob(@PathVariable("id") String id) {
        return ResponseEntity.ok(jobService.retrieveJob(id));
    }

    @GetMapping()
    @ApiOperation(value = "Retrieve all jobs")
    public ResponseEntity<?> retrieveAllJobs() {
        return ResponseEntity.ok(jobService.retrieveAllJobs());
    }
}
