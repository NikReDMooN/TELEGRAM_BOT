package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GustDaoImp implements GustDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addGust(Gust gust) {
        entityManager.persist(gust);
    }

    @Override
    public Gust getGust(String id) {
        Query query = entityManager.createQuery("SELECT g FROM Gust g WHERE g.id = :id");
        query.setParameter("id", id);
        return (query.getResultList().size() > 0) ? (Gust) query.getResultList().get(0) : null;
    }

    @Override
    public void deleteGust(String id) {
        Query query = entityManager.createQuery("SELECT g FROM Gust g WHERE g.id = :id");
        query.setParameter("id", id);
        Gust gust = (Gust) query.getResultList().get(0);

        Performance performance = gust.getPerformance();
        performance.setCountOfSeats(performance.getCountOfSeats() + 1);
        entityManager.merge(performance);

        query = entityManager.createQuery("DELETE FROM Gust g WHERE g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
