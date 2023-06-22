package com.pisarenko.telegram_bot.config;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;
import com.pisarenko.telegram_bot.dto.PerformanceData;
import com.pisarenko.telegram_bot.service.GustService;
import com.pisarenko.telegram_bot.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TestFunctional {

    @Autowired
    public PerformanceService performanceService;

    @Autowired
    public GustService gustService;


    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        Performance performance = new Performance();
        performance.setName("GUMLET");
        PerformanceData data = new PerformanceData();
        data.setData(new Timestamp(System.currentTimeMillis()));
        performance.setData(data);
        performanceService.addPerformance(performance);
        Performance delete = new Performance();
        delete.setName("CHEHOV");
        PerformanceData yes = new PerformanceData();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate yesterday = currentTime.toLocalDate().minusDays(1);
        yes.setData(Timestamp.valueOf(yesterday.atStartOfDay()));
        delete.setData(yes);
        performanceService.addPerformance(delete);
        performanceService.deleteOldPerformance();
        Performance performancefuture = new Performance();
        LocalDate tommorow = currentTime.toLocalDate().plusDays(2);
        PerformanceData no = new PerformanceData();
        no.setData(Timestamp.valueOf(tommorow.atStartOfDay()));
        performancefuture.setData(no);
        performancefuture.setName("I DON'T  KNOW, FUCK");
        performanceService.addPerformance(performancefuture);
        performanceService.deleteOldPerformance();
        Gust gust1 = new Gust();
        Gust gust2 = new Gust();
        gust1.setId("alalallala");
        gust2.setId("qqqq");
        gust1.setPerformance(performancefuture);
        gust2.setPerformance(performancefuture);
        gustService.addGust(gust1);
        gustService.addGust(gust2);
        performanceService.addGust(performancefuture, gust1);
        performanceService.addGust(performancefuture, gust2);



    }
}
