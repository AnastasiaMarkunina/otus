package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Double> sortedData = new TreeMap<>(data);
            mapper.writeValue(new File(fileName), sortedData);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
