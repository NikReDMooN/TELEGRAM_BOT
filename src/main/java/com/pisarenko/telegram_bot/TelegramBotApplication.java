package com.pisarenko.telegram_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


//TODO сделай реализацию main бота путем создания наследника от user и переопредели метод docommand
//TODO исправь везде логику с performance, у тебя новая теперь сущность отвечающая за имя
@SpringBootApplication
@PropertySource("classpath:telegram.properties")
public class TelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
