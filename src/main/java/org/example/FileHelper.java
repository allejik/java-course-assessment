package org.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

// Класс помощник для получиния избирателей и сохранения статистики
class FileHelper {

    private static FileHelper INSTANCE;

    private final ObjectMapper objectMapper;

    private FileHelper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static FileHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileHelper();
        }

        return INSTANCE;
    }

    // Читает файл voters.json и возвращает массив объектов Voter
    Voter[] readVoters() throws IOException {
        InputStream inputStream = FileHelper.class.getClassLoader().getResourceAsStream("voters.json");

        return objectMapper.readValue(inputStream, Voter[].class);
    }

    // Создает stats.json на основе Statistics
    void writeStatistics(Statistics statistics) throws IOException {
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("stats.json"), statistics);
    }
}