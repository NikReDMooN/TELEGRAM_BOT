package com.pisarenko.telegram_bot.service;

import com.pisarenko.telegram_bot.dto.Performance;
import com.pisarenko.telegram_bot.dto.PerformanceData;
import lombok.Getter;
import lombok.SneakyThrows;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Component
@Getter
public class AdminTelegramBot extends TelegramLongPollingBot {

    private final Map<Long, Message> previousMessageById;


    private final String botUsername;
    private final String botToken;

    private final PerformanceService performanceService;
    private final GustService gustService;

    public AdminTelegramBot(TelegramBotsApi telegramBotsApi,
                            @Value("${telegram-bot-admin.name}") String botUsername,
                            @Value("${telegram-bot-admin.token}") String botToken,
                            PerformanceService performanceService, GustService gustService) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.performanceService = performanceService;
        this.gustService = gustService;

        previousMessageById = new HashMap<>();

        telegramBotsApi.registerBot(this);
    }

    @Override
    @SneakyThrows
    public void onRegister() {
/*        SendMessage entity = new SendMessage();
        entity.setText("START WORKING ON " + LocalDateTime.now().atZone(ZoneId.of("Europe/Moscow")));
        entity.setChatId("917631670");
        execute(entity);*/
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Long id = update.getMessage().getFrom().getId();

        if (id != 917631670) return;

        if (update.getMessage() != null) handleMessage(update.getMessage());
        if (update.getCallbackQuery() != null) handleCallBackQuery(update.getCallbackQuery());


    }

    public void handleMessage (Message message) {

      //TODO - оптимизируй работу с id, чтобы постоянно его не вытаскивать
        if (message.hasText() &&  previousMessageById.containsKey(message.getFrom().getId())
                && previousMessageById.get(message.getFrom().getId()).getText().equals("/create_a_performance")) {
            doCommand(message, "/create_a_performance");
        }

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
            case "/create_a_performance" -> createPerformance(message);
            case "/delete_past_performance" -> deletePastPerfrormance(message);
        }
    }

    @SneakyThrows
    public void deletePastPerfrormance (Message message) {
        performanceService.deleteOldPerformance();

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Прошлые выступления успешно удалены");

        execute(sendMessage);
    }


    @SneakyThrows
    public void createPerformance (Message message) {

        if (previousMessageById.containsKey(message.getFrom().getId())
                && previousMessageById.get(message.getFrom().getId()) != null
                && previousMessageById.get(message.getFrom().getId()).getText().equals("/create_a_performance")) {

            String text = message.getText();
            String[] parts = text.split("\n");

            System.out.println(parts[0] + "   " + parts[1]);

            String date = parts[0];
            date += ":00";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateHelper = dateFormat.parse(date);

            PerformanceData performanceData = new PerformanceData();
            performanceData.setData(new Timestamp(dateHelper.getTime()));

            Performance performance = new Performance();
            performance.setName(parts[1]);
            performance.setData(performanceData);

            performanceService.addPerformance(performance);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText("ВЫСТУПЛЕНИЕ УСПЕШНО ДОБАВЛЕНО\n" +
                    performance.getName() + "\n" + performanceData.writeData());
            execute(sendMessage);

            previousMessageById.put(message.getFrom().getId(), null);

            return;
        }


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Введите формат нового представления в следующем виде: " + "\n" +
                "yyyy-MM-dd HH:mm" + "\n" +
                "НАЗВАНИЕЕ ПЬЕСЫ");

        previousMessageById.put(message.getFrom().getId(), message);

        execute(sendMessage);

    }

    public void handleCallBackQuery (CallbackQuery callbackQuery) {

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }



}
