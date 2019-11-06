package com.chukkykatz.cooking.scheduler;

import com.chukkykatz.cooking.domain.Dish;
import com.chukkykatz.cooking.domain.Receipt;
import com.chukkykatz.cooking.service.AdviceService;
import com.chukkykatz.cooking.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdviceJob extends QuartzJobBean {

    @Override
    protected void executeInternal(final JobExecutionContext jobExecutionContext) {
        try {
            final ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext
                    .getScheduler().getContext().get("applicationContext");
            final AdviceService adviceService = applicationContext.getBean(AdviceService.class);
            final EmailService emailService = applicationContext.getBean(EmailService.class);
            final Map<Dish, Receipt> advices = adviceService.getAdvice();
            if (advices.size() > 0) {
                emailService.sendAdviceMessage(advices);
                log.info("Advice provided: " + advices.keySet()
                        .stream()
                        .map(Dish::getName)
                        .collect(Collectors.joining(", ")));
            } else {
                log.info("No scheduled advice yet");
            }
        } catch (SchedulerException e) {
            log.error("Fail to provide advice email", e);
        }
    }
}
