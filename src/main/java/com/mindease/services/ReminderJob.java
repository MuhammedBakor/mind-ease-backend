package com.mindease.services;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReminderJob implements Job {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) {
        String email = context.getMergedJobDataMap().getString("email");
        String notes = context.getMergedJobDataMap().getString("notes");

        emailService.sendReminderEmail(email, notes);
    }
}
