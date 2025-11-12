package com.mindease.services;


import com.mindease.entities.ReminderEntity;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class ReminderSchedulerService {

    private final Scheduler scheduler;

    public ReminderSchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void scheduleReminder(ReminderEntity reminder) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ReminderJob.class)
                .withIdentity("reminderJob_" + reminder.getId())
                .usingJobData("email", reminder.getUser().getEmail())
                .usingJobData("notes", reminder.getNotes())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("reminderTrigger_" + reminder.getId())
                .startAt(Date.from(reminder.getDueDateTime()
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
