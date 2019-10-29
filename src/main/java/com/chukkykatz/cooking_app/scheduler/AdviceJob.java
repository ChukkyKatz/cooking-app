package com.chukkykatz.cooking_app.scheduler;

import com.chukkykatz.cooking_app.domain.Dish;
import com.chukkykatz.cooking_app.domain.Receipt;
import com.chukkykatz.cooking_app.service.AdviceService;
import com.chukkykatz.cooking_app.service.EmailService;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.Map;

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
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
