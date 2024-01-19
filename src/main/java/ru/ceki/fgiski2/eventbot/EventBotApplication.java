package ru.ceki.fgiski2.eventbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class EventBotApplication {
    @Autowired
    private EventBot eventBot;
    @Autowired
    private EventService eventService;

    public static void main(String[] args) {
        SpringApplication.run(EventBotApplication.class);
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
          this.eventBot.setQueue(this.eventService.getQueue());
          TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
          this.eventService.setBotSession(botsApi.registerBot(eventBot));
          this.eventService.start();
        };
    }
}