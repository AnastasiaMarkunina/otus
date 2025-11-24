package ru.example;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import ru.example.annotation.After;
import ru.example.annotation.Before;
import ru.example.annotation.Test;

public class Run {

    public static void runTests(Class<?> testClass) throws Exception {

        Object testInstance = testClass.getDeclaredConstructor().newInstance();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        // Собираем методы с аннотациями
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        int passed = 0;
        int failed = 0;

        // Запускаем тесты
        for (Method testMethod : testMethods) {
            System.out.println("Run: " + testMethod.getName());

            try {
                // Выполняем @Before методы
                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(testInstance);
                }

                // Запускаем тест
                testMethod.invoke(testInstance);
                passed++;

            } catch (Exception e) {
                failed++;
            } finally {
                // Выполняем @After методы
                for (Method afterMethod : afterMethods) {
                    try {
                        afterMethod.invoke(testInstance);
                    } catch (Exception e) {
                        System.out.println("Warning: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println(
                "Statistics all test: " + passed + " passed, " + failed + " failed, " + (passed + failed) + " total");
    }
}
