package ru.otus.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.crm.annotation.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;
    private final Field idField;

    @SuppressWarnings("unchecked")
    public EntityClassMetaDataImpl() {
        // Получаем generic-параметр из контекста использования
        // Это упрощенный подход - в реальности нужно использовать TypeToken или аналоги
        try {
            // В данном контексте класс определяется при создании в HomeWork
            // Более правильный подход - передавать Class<T> в конструктор
            // Но для задания будем использовать рефлексию
            throw new IllegalStateException("Этот конструктор требует доработки для определения generic-типа");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Альтернативный конструктор с явным указанием класса
    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.allFields = Arrays.asList(clazz.getDeclaredFields());
        this.allFields.forEach(f -> f.setAccessible(true));

        this.idField = allFields.stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("No field with @Id annotation found in " + clazz.getName()));

        this.fieldsWithoutId =
                allFields.stream().filter(f -> !f.equals(idField)).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Constructor<T> getConstructor() {
        try {
            return (Constructor<T>) clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No default constructor found in " + clazz.getName(), e);
        }
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return new ArrayList<>(allFields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return new ArrayList<>(fieldsWithoutId);
    }
}
