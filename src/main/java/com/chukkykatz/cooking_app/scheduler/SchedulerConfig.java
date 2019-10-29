package com.chukkykatz.cooking_app.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

@Configuration
public class SchedulerConfig {

    @Value("${application.job.schedule}")
    private String jobSchedule;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        final SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactoryBean.setJobDetails(jobDetailFactoryBean().getObject());
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        return schedulerFactoryBean;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        final JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setName("cooking-advice-job");
        jobDetailFactoryBean.setJobClass(AdviceJob.class);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        final CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("cooking-advice-job-cron-trigger");
        cronTriggerFactoryBean.setCronExpression(jobSchedule);
        cronTriggerFactoryBean.setStartTime(new Date());
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        return cronTriggerFactoryBean;
    }
}
