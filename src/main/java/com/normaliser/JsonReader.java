package com.normaliser;

import com.classes.Job;
import com.classes.Position;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonReader {

    /*This class is responsably for read and update the JSON Memory File*/
    private String fileName;

    /*Depedency injection*/
    public JsonReader(String fileName) {
        this.fileName = fileName;
    }

    private List<Job> readPositionsFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        List<Position> jobJsonList = mapper.readValue(inputStream, new TypeReference<List<Position>>() {});

        List<Job> jobsPosition = new ArrayList<>();
        for (Position job : jobJsonList) {
            jobsPosition.add(new Position(job.getJobTitle(), job.getKeywords().stream().collect(Collectors.toSet())));
        }

        return jobsPosition;
    }

    public List<Job> read() {
        List<Job> jobsList = new ArrayList<>();
        try {
            jobsList = readPositionsFromJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobsList;
    }

    public void updateJson(List<Job> jobsPosition) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/resources/memory.json"), jobsPosition );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
