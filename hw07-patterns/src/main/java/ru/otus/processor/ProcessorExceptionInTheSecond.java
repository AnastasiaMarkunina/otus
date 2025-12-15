package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorExceptionInTheSecond implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorExceptionInTheSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {

        if (dateTimeProvider.getDate().getSecond() % 2 == 0) {
            throw new IllegalStateException("This is an error");
        }

        return message;
    }
}
