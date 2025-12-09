package ru.otus.processor;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

class TestProcessor {

    // todo: 2. Сделать процессор, который поменяет местами значения field11 и field12
    @Test
    void processChangeField() {
        Message original = new Message.Builder(1L).field11("11").field12("12").build();

        System.out.println("Before method change field");
        System.out.println("Field11 = " + original.getField11());
        System.out.println("Field12 = " + original.getField12());

        Processor processor = new ProcessorChangeFields11and12();
        Message processed = processor.process(original);

        System.out.println("After method change field");
        System.out.println("Field11 = " + processed.getField11());
        System.out.println("Field12 = " + processed.getField12());
    }

    // todo: 3. Сделать процессор, который будет выбрасывать исключение в четную секунду
    @Test
    void processExceptionInTheSecond() {

        Message message = null;

        DateTimeProvider time1 = () -> LocalDateTime.of(2025, 12, 10, 0, 11, 0);
        Processor processor1 = new ProcessorExceptionInTheSecond(time1);
        DateTimeProvider time2 = () -> LocalDateTime.of(2025, 12, 10, 0, 11, 1);
        Processor processor2 = new ProcessorExceptionInTheSecond(time2);

        assertThrows(IllegalStateException.class, () -> processor1.process(message));
        assertDoesNotThrow(() -> processor2.process(message));
    }
}
