package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.dto.Gust;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GustDaoImp implements GustDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addGust(Gust gust) {
        entityManager.persist(gust);
    }
}
