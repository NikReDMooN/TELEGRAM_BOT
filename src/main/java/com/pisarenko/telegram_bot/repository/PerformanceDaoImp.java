package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.entity.Gust;
import com.pisarenko.telegram_bot.entity.Performance;
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

    @Override
    public List<Performance> getAllPerformances() {
        Query query = entityManager.createQuery("Select p From Performance p " +
                "LEFT JOIN PerformanceData d ON p.data = d " +
                "LEFT JOIN FETCH p.gusts WHERE p.id = p.id and p.CountOfSeats > 0");
        return query.getResultList();
    }

    @Override
    public Performance getPerformanceByName(String name) {
        Query query = entityManager.createQuery("Select p FROM  Performance p " +
                "WHERE p.name = :performancename ");
        query.setParameter("performancename",name);
        return (Performance) query.getResultList().get(0);
    }
}
