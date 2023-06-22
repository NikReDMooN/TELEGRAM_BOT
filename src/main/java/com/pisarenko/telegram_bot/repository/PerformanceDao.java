package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public interface PerformanceDao {


    void addGust(Performance performance, Gust gust);

    void addPerformance(Performance performance);

    void deleteOldPerformance();

}
