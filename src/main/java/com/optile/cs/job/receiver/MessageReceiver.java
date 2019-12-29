package com.optile.cs.job.receiver;

import com.optile.cs.job.JobRepository;
import com.optile.cs.job.receiver.model.EventMessage;
import com.optile.cs.job.receiver.model.StatusMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class MessageReceiver {
    @Autowired
    private JobRepository jobRepository;

    @JmsListener(destination = "job-status")
    public void statusListener(StatusMessage statusMessage) {
        try {
            jobRepository.updateJobStatus(statusMessage.getJobId(), statusMessage.getJobStatus());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @JmsListener(destination = "job-event")
    public void eventListener(EventMessage eventMessage) {
        try {
            log.info(String.format("%s, %s", eventMessage.getJobId(), eventMessage.getMessage()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
