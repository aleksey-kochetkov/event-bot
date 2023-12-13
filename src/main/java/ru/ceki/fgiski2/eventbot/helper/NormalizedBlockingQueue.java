package ru.ceki.fgiski2.eventbot.helper;

import java.util.concurrent.ArrayBlockingQueue;

public class NormalizedBlockingQueue<E> extends ArrayBlockingQueue<E> {

    public NormalizedBlockingQueue(int capacity) {
        super(capacity);
    }
}