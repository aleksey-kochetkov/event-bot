package ru.ceki.fgiski2.eventbot;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.generics.BotSession;

@Service
public class EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private BotSession botSession;
    @Autowired
    private EventBotLogic logic;

    public void setBotSession(BotSession botSession) {
        this.botSession = botSession;
    }
    
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::waitToShutdown));
    }

    public void waitToShutdown() {
        try {
            this.stopBotSession();
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void stopBotSession() {
        if (this.botSession.isRunning()) {
            this.botSession.stop();
        }
    }
}