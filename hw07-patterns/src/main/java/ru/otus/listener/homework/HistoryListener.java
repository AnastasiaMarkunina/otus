package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> historyListener = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {

        Message historyMsg;

        if (msg.getField13() != null) {
            ObjectForMessage historyField13 = msg.getField13();
            ObjectForMessage newField13 = new ObjectForMessage();
            newField13.setData(new ArrayList<>(historyField13.getData()));
            historyMsg = msg.toBuilder().field13(newField13).build();
        } else {
            historyMsg = msg.toBuilder().build();
        }

        historyListener.put(msg.getId(), historyMsg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {

        return Optional.ofNullable(historyListener.get(id));
    }
}
