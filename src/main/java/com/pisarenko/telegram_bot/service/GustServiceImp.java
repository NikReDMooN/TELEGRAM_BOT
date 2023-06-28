package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.entity.Gust;
import com.pisarenko.telegram_bot.repository.GustDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GustServiceImp implements GustService {

    private final GustDAO gustDAO;

    @Autowired
    public GustServiceImp(GustDAO gustDAO) {
        this.gustDAO = gustDAO;
    }

    @Override
    @Transactional
    public void addGust(Gust gust) {
        gustDAO.addGust(gust);
    }

    @Override
    @Transactional
    public Gust getGust(String id) {
        return gustDAO.getGust(id);
    }

    @Override
    @Transactional
    public void deleteGust(String id) {
        gustDAO.deleteGust(id);
    }
}
