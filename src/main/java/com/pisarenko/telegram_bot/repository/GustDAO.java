package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.entity.Gust;

public interface GustDAO {

    void addGust (Gust gust);

    Gust getGust (String id);

    void deleteGust(String id);

}
