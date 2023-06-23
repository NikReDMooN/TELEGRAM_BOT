package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;
import com.pisarenko.telegram_bot.repository.PerformanceDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImp implements PerformanceService{

    private final PerformanceDao performanceDao;

    @Autowired
    public PerformanceServiceImp(PerformanceDao performanceDao) {
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

    @Override
    @Transactional
    public List<Performance> getAllPerformances() {
        return performanceDao.getAllPerformances();
    }

    @Override
    @Transactional
    public Performance getPerformanceByName(String name) {
        return performanceDao.getPerformanceByName(name);
    }
}
