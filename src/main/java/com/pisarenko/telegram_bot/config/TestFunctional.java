package com.pisarenko.telegram_bot.config;

import com.pisarenko.telegram_bot.entity.Gust;
import com.pisarenko.telegram_bot.entity.Performance;
import com.pisarenko.telegram_bot.entity.PerformanceData;
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

    public final PerformanceService performanceService;

    public final GustService gustService;

    @Autowired
    public TestFunctional(PerformanceService performanceService, GustService gustService) {
        this.performanceService = performanceService;
        this.gustService = gustService;
    }


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
        Performance test1 = new Performance();
        PerformanceData testData = new PerformanceData();
        testData.setData(Timestamp.valueOf(tommorow.atTime(2,2)));
        test1.setData(testData);
        testData = new PerformanceData();
        testData.setData(Timestamp.valueOf(tommorow.atTime(3,3)));
        Performance test2 = new Performance();
        test2.setData(testData);
        test1.setName("GUMLET");
        test2.setName("PISARENKO GENIUS");
        performanceService.addPerformance(test2);
        performanceService.addPerformance(test1);


        Performance performanceNoseats = new Performance();
        performanceNoseats.setName("WAR AND PEACE");
        performanceNoseats.setCountOfSeats(0);
        PerformanceData data1 = new PerformanceData();
        data1.setData(Timestamp.valueOf(tommorow.atTime(5,5)));
        performanceNoseats.setData(data1);
        Performance performanceNoseatsWithGusts = new Performance();
        PerformanceData data2 = new PerformanceData();
        data2.setData(Timestamp.valueOf(tommorow.atTime(6,6)));
        performanceNoseatsWithGusts.setData(data2);
        performanceNoseatsWithGusts.setName("NORWEGIAN WOOD");
        performanceNoseatsWithGusts.setCountOfSeats(0);
        Gust gust = new Gust();
        gust.setId("NIKITA");
        Gust gust3 = new Gust();
        gust3.setId("VLAS");
        gustService.addGust(gust);
        gustService.addGust(gust3);
        performanceService.addPerformance(performanceNoseats);
        performanceService.addPerformance(performanceNoseatsWithGusts);
        performanceService.addGust(performanceNoseatsWithGusts, gust);
        performanceService.addGust(performanceNoseatsWithGusts, gust3);

        for (int i = 0; i < 28; i++) {
            Gust gust4 = new Gust();
            gust4.setId("" + i);
            gust4.setPerformance(performanceNoseatsWithGusts);
            gustService.addGust(gust4);
            performanceService.addGust(performanceNoseatsWithGusts, gust4);
        }

        performance = new Performance();
        data = new PerformanceData();
        LocalDate today = currentTime.toLocalDate();
        data.setData(Timestamp.valueOf(today.atTime(1,1)));
        performance.setData(data);
        performance.setName("I NEED TO BE DELETED");

        performanceService.addPerformance(performance);

    }
}
