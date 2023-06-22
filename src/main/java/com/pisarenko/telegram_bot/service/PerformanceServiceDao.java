package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;
import com.pisarenko.telegram_bot.repository.PerformanceDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerformanceServiceDao implements PerformanceService{

    private PerformanceDao performanceDao;

    @Autowired
    public PerformanceServiceDao(PerformanceDao performanceDao) {
        this.performanceDao = performanceDao;
    }

    @Override
    @Transactional
    public void addGust(Performance performance, Gust gust) {
        performanceDao.addGust(performance, gust);
    }

    @Override
    @Transactional
    public void addPerformance(Performance performance) {
        performanceDao.addPerformance(performance);
    }

    @Override
    @Transactional
    public void deleteOldPerformance() {
        performanceDao.deleteOldPerformance();
    }

}
