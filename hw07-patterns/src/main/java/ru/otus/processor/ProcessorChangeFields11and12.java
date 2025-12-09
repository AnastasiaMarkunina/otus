package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorChangeFields11and12 implements Processor {

    @Override
    public Message process(Message message) {

        String newField11 = message.getField11();
        String newField12 = message.getField12();

        return message.toBuilder().field11(newField12).field12(newField11).build();
    }
}
