package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PerformanceDaoImp implements PerformanceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addGust(Performance performance, Gust gust) {
        Performance performanceDTO = entityManager.find(Performance.class, performance.getId());
        performanceDTO.getGusts().add(gust);
        performanceDTO.setCountOfSeats(performanceDTO.getCountOfSeats() - 1);
        entityManager.merge(performanceDTO);
    }
    
    @Override
    public void addPerformance(Performance performance) {
        entityManager.persist(performance);

    }

    @Override
    public void deleteOldPerformance() {
        Query query = entityManager.createQuery("Select p From Performance p JOIN PerformanceData d ON p.data = d");
        List<Performance> performanceList = query.getResultList();
        for(Performance performance : performanceList) {
            if (performance.getData().getData().before(new Timestamp(System.currentTimeMillis()))) {
                entityManager.remove(performance);
            }
        }
    }
}
