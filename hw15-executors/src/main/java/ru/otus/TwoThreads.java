package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoThreads {
    private static final Logger logger = LoggerFactory.getLogger(TwoThreads.class);

    private int currentValue = 1;
    private boolean increasing = true;
    private int turn = 1; // 1 - печатает первый поток, 2 - печатает второй поток
    private boolean firstThreadPrinted = false;
    private volatile boolean running = true;

    public synchronized void printNumbers(int threadId) {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                // Ждем своей очереди
                while ((turn != threadId || (threadId == 2 && !firstThreadPrinted)) && running) {
                    wait();
                }

                if (!running) break;

                // Печатаем текущее значение
                logger.info("Поток {}: {}", threadId, currentValue);

                if (threadId == 1) {
                    firstThreadPrinted = true;
                    turn = 2;
                } else {
                    firstThreadPrinted = false;
                    turn = 1;

                    if (currentValue >= 10) {
                        increasing = false;
                    } else if (currentValue <= 1) {
                        increasing = true;
                    }

                    if (increasing) {
                        currentValue++;
                    } else {
                        currentValue--;
                    }
                }

                notifyAll();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
        synchronized (this) {
            notifyAll();
        }
    }

    public static void main(String[] args) {
        TwoThreads printer = new TwoThreads();

        Thread thread1 = new Thread(() -> printer.printNumbers(1), "Thread-1");
        Thread thread2 = new Thread(() -> printer.printNumbers(2), "Thread-2");

        thread1.start();
        thread2.start();

        // Даем поработать 10 секунд
        try {
            Thread.sleep(10000);
            printer.stop();
            thread1.join(1000);
            thread2.join(1000);
        } catch (InterruptedException e) {
            logger.error("Main thread interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
