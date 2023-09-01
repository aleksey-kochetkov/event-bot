package ru.ceki.fgiski2.eventbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventBot extends TelegramLongPollingBot {
    @Value("${event-bot.bot-username}")
    private String botUsername;
    @Value("${event-bot.bot-token}")
    private String botToken;
    private Queue<QueueElement> consumer;

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
    }
}