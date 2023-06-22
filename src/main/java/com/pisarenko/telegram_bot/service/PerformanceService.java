package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;

public interface PerformanceService {

    void addGust(Performance performance, Gust gust);

    void addPerformance(Performance performance);

    void deleteOldPerformance();

}
