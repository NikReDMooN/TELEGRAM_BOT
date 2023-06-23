package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.dto.Gust;

public interface GustService {

    void addGust (Gust gust);

    Gust getGust (String id);

}
