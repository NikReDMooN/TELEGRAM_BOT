package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.entity.Gust;
import com.pisarenko.telegram_bot.entity.Performance;

import java.util.List;

public interface PerformanceService {

    void addGust(Performance performance, Gust gust);

    void addPerformance(Performance performance);

    void deleteOldPerformance();

    List<Performance> getAllPerformances();

    Performance getPerformanceByName(String name);

}
