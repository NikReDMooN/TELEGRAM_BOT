package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.entity.Gust;

public interface GustService {

    void addGust (Gust gust);

    Gust getGust (String id);

    void deleteGust(String id);

}
