package ru.example;

import ru.example.annotation.Log;

public class Logging implements LogInterface {

    @Log
    @Override
    public void calculation(int a) {
        System.out.println("calculation " + a);
    }

    @Log
    @Override
    public void calculation(int a, int b) {
        System.out.println("calculation " + (a + b));
    }

    @Log
    @Override
    public void calculation(int a, int b, String c) {
        System.out.println("calculation " + (a + b) + ", " + c);
    }
}
