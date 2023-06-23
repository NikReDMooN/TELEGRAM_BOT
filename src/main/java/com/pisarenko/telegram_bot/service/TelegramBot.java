package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.dto.Gust;
import com.pisarenko.telegram_bot.dto.Performance;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final SendMessage response = new SendMessage();

    private final String botUsername;
    private final String botToken;
    private final String generatedKey;

    private final PerformanceService performanceService;
    private final GustService gustService;

    @Autowired
    public TelegramBot(
            TelegramBotsApi telegramBotsApi,
            @Value("${telegram-bot.name}") String botUsername,
            @Value("${telegram-bot.token}") String botToken,
            @Value(("${generated-key}")) String generatedKey,
            PerformanceService performanceService, GustService gustService) throws TelegramApiException {
        this.performanceService = performanceService;
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.generatedKey = generatedKey;
        this.gustService = gustService;

        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.getMessage() != null) handleMessage(update.getMessage());
        if (update.getCallbackQuery() != null) handleCallBackQuery(update.getCallbackQuery());
    }

    @SneakyThrows
    public void handleCallBackQuery(CallbackQuery callbackQuery) {
        SendMessage message = new SendMessage();

        Gust gust = new Gust();
        gust.setId(callbackQuery.getFrom().getId().toString());

        Performance performance = performanceService.getPerformanceByName(callbackQuery.getData().split("\n")[2]);

        gust.setPerformance(performance);
        gustService.addGust(gust);
        performanceService.addGust(performance, gust);

        message.setChatId(callbackQuery.getMessage().getChatId().toString());
        message.setText("ЖДЕМ ВАС НА НАШЕМ СПЕКТАКЛЕ:" + "\n" + performance.getData().writeData()
                + "\n" + performance.getName());
        execute(message);
    }

    public void handleMessage (Message message) {
        System.out.println(message);
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst(); //ищем команду
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(),
                        commandEntity.get().getLength());
                doCommand( message, command);

            }
        }
    }

    public void doCommand(Message message, String command) {
        switch (command) {
            case "/reserve_a_seat" -> reserve_a_seat(message);
            case "/delete_a_seat" -> deleteASeat(message);
            case "/new" -> sendNews(message);
        }
    }

    public void sendNews(Message message) {

    }

    @SneakyThrows
    public void deleteASeat(Message message) {
       var id = message.getFrom().getId();
       gustService.deleteGust(id.toString());

       SendMessage sendMessage = new SendMessage();
       sendMessage.setChatId(message.getChatId().toString());
       sendMessage.setText("Ваша бронь успешно отменина :)");
       execute(sendMessage);
    }


    @SneakyThrows
    public void reserve_a_seat(Message message)  {

        if (gustService.getGust(message.getFrom().getId().toString()) != null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText("вы уже забронировали место, к сожалению, больше нельзя :(");
            execute(sendMessage);
            return;
        }


        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();

        InlineKeyboardButton keyboardButton;
        List<Performance> performanceList = performanceService.getAllPerformances();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for(Performance p:performanceList) {
            keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(p.getData().writeData() + "\n" + p.getName());
            keyboardButton.setCallbackData(generatedKey +"\n" + p.getData().writeData() + "\n" + p.getName());
            row.add(keyboardButton);
            rows.add(row);
            row = new ArrayList<>();
        }

        if (!rows.contains(row)) {
            rows.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(rows);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Выберите дату, пожалуйста");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        execute(sendMessage);

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @SneakyThrows
    @Override
    public void onRegister() {
/*        SendMessage entity = new SendMessage();
        entity.setText("START WORKING ON " + LocalDateTime.now().atZone(ZoneId.of("Europe/Moscow")));
        entity.setChatId("917631670");
        execute(entity);*/

    }
}
