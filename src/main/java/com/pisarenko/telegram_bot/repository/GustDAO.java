package com.pisarenko.telegram_bot.repository;

import com.pisarenko.telegram_bot.dto.Gust;

public interface GustDAO {

    void addGust (Gust gust);

    Gust getGust (String id);

}
