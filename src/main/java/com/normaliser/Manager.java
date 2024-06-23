package com.normaliser;

import com.classes.Job;
import com.classes.StringUtils;
import com.exceptions.JobNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Manager {

    /*This Class is responsable to use de Normalization algorithm, like a factory that abstract the Normaliser*/

    /*Singletons pattern*/
    private static Manager manager;

    private JsonReader jsonReader;
    private List<Job> jobsPosition;
    private Normaliser normaliser;
    private String jobPositionNormalised;

    private Manager(JsonReader jsonReader, Normaliser normaliser){
        this.jsonReader = jsonReader;
        this.normaliser = normaliser;
        this.jobsPosition = jsonReader.read();
        this.jobPositionNormalised = "";
    }

    public static synchronized Manager getInstance(JsonReader jsonReader, Normaliser normaliser) {
        if (manager == null) {
            manager = new Manager(jsonReader, normaliser);
        }
        return manager;
    }

    public Set<Job> normalise(String jobPosition) {

        jobPositionNormalised = StringUtils.normaliseStringPosition(jobPosition);

        try {
            TreeMap<Integer, Set<Job>> mapScores = normaliser.normalize(jobPositionNormalised, jobsPosition);
            return mapScores.lastEntry().getValue();
        } catch (RuntimeException e) {
            return null;
        }

    }

    public void saveBestAnswerInMemory(Job bestAnswer) throws Exception {
        Job jobFound = jobsPosition.stream().filter(job -> job.equals(bestAnswer)).findFirst().orElseThrow(() -> new JobNotFoundException());
        jobFound.updateKeyWords(jobPositionNormalised.split(" "));
        jsonReader.updateJson(jobsPosition);
    }

}
