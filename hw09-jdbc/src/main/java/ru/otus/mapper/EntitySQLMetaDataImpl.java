package ru.otus.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT * FROM %s", entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "SELECT * FROM %s WHERE %s = ?",
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();

        sql.append("INSERT INTO ")
                .append(entityClassMetaData.getName().toLowerCase())
                .append(" (");

        List<String> fieldNames = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.toList());

        for (int i = 0; i < fieldNames.size(); i++) {
            sql.append(fieldNames.get(i));
            values.append("?");
            if (i < fieldNames.size() - 1) {
                sql.append(", ");
                values.append(", ");
            }
        }

        sql.append(") VALUES (").append(values).append(")");
        return sql.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE ")
                .append(entityClassMetaData.getName().toLowerCase())
                .append(" SET ");

        List<String> fieldNames = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.toList());

        for (int i = 0; i < fieldNames.size(); i++) {
            sql.append(fieldNames.get(i)).append(" = ?");
            if (i < fieldNames.size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(" WHERE ").append(entityClassMetaData.getIdField().getName()).append(" = ?");
        return sql.toString();
    }
}
