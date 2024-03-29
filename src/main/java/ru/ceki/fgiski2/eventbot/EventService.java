package ru.ceki.fgiski2.eventbot;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.generics.BotSession;
import ru.ceki.fgiski2.eventbot.helper.NormalizedBlockingQueue;
import ru.ceki.fgiski2.eventbot.dto.QueueElement;

@Service
public class EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private BotSession botSession;
    private CountDownLatch running;
    private final Queue<QueueElement> queue = new NormalizedBlockingQueue<>(16);
    @Autowired
    private EventBotLogic logic;

    public void setBotSession(BotSession botSession) {
        this.botSession = botSession;
    }
    
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::waitToShutdown));
        this.running = new CountDownLatch(1);
        new Thread(this::run).start();
    }

    public void run() {
    }

    public void waitToShutdown() {
        try {
            this.stopBotSession();
            this.running.await(5, TimeUnit.SECONDS);
//            LOGGER.info("queue.size():{}", this.queue.size());
            if (this.queue.size() > 0) {
                System.out.format("queue.size():%d%n", this.queue.size());
            }
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void stopBotSession() {
        if (this.botSession.isRunning()) {
            this.botSession.stop();
        }
    }

    public Queue<QueueElement> getQueue() {
        return this.queue;
    }
}