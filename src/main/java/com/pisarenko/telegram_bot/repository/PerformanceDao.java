package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.entity.Gust;
import com.pisarenko.telegram_bot.entity.Performance;

import java.util.List;

public interface PerformanceDao {


    void addGust(Performance performance, Gust gust);

    void addPerformance(Performance performance);

    void deleteOldPerformance();

    List<Performance> getAllPerformances();

    Performance getPerformanceByName(String name);

}
