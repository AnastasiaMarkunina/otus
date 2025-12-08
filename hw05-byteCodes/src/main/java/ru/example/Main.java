package ru.example;

public class Main {
    public void action() throws ReflectiveOperationException {
        LogInterface log = ProxyFactory.createLoggedProxy(Logging.class, LogInterface.class);
        log.calculation(2);
        log.calculation(2, 3);
        log.calculation(2, 3, "test");
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        new Main().action();
    }
}
