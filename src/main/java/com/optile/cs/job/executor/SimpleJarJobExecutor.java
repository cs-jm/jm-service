package com.optile.cs.job.executor;

import com.optile.cs.job.model.Job;

import java.io.IOException;

public class SimpleJarJobExecutor extends JobExecutor {
    @Override
    public void execute(Job job) {
        int error = 0;
        try {
         error =   new ProcessBuilder().command("Java", "-Jar", job.getFileLocation()).start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
