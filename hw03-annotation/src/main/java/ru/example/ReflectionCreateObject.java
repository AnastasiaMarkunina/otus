package ru.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import ru.example.annotation.After;
import ru.example.annotation.Before;
import ru.example.annotation.Test;

public class ReflectionCreateObject {
    private Class<Cat> clazz;
    private Cat cat;

    @Before
    void setUp() {
        System.out.println("=== The cat get up ===");
        clazz = Cat.class;
    }

    @After
    void tearDown() {
        System.out.println("=== The cat lies down ===");
        cat = null;
        System.out.println();
    }

    @Test
    void testClassInfo() {
        String className = clazz.getSimpleName();
        System.out.println("Class Name: " + className);
        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println("Constructors: " + Arrays.toString(constructors));
    }

    @Test
    void testCreateObjectWithReflection() throws Exception {
        Constructor<Cat> constructor = clazz.getConstructor(String.class, String.class);
        cat = constructor.newInstance("Vasya", "male");
        System.out.println("Cat info: " + cat.getInfo());
    }

    @Test
    void testInvokePrivateMethod() throws Exception {
        Constructor<Cat> constructor = clazz.getConstructor(String.class, String.class);
        cat = constructor.newInstance("Murka", "female");
        Method privateMethod = clazz.getDeclaredMethod("getMay");
        privateMethod.setAccessible(true);
        privateMethod.invoke(cat);
        System.out.println("Private method invoked successfully");
    }

    @Test
    void testAccessPrivateField() throws Exception {
        Constructor<Cat> constructor = clazz.getConstructor(String.class, String.class);
        cat = constructor.newInstance("Barsik", "male");
        int initialAge = cat.getAge();
        Field privateField = clazz.getDeclaredField("age");
        privateField.setAccessible(true);
        privateField.set(cat, 5);
        int newAge = (int) privateField.get(cat);
        System.out.println("New age: " + newAge);
        System.out.println("New cat info: " + cat.getInfo());
    }

    @Test
    void testConstructorWithAllParameters() throws Exception {
        Constructor<Cat> constructor = clazz.getConstructor(String.class, int.class, String.class, String.class);
        cat = constructor.newInstance("Persik", 3, "male", "Persian");
        System.out.println("Full parameter cat: " + cat.getInfo());
    }

    @Test
    void testInvalidGenderThrowsException() throws Exception {
        Constructor<Cat> constructor = clazz.getConstructor(String.class, String.class);
        System.out.println("Exception thrown correctly for invalid gender");
    }
}
