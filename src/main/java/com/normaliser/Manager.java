package com.normaliser;

import com.classes.Job;
import com.classes.Position;
import com.classes.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    private Manager(JsonReader jsonReader, Normaliser normaliser){
        this.jsonReader = jsonReader;
        this.normaliser = normaliser;
        this.jobsPosition = jsonReader.read();
    }

    public static synchronized Manager getInstance(JsonReader jsonReader, Normaliser normaliser) {
        if (manager == null) {
            manager = new Manager(jsonReader, normaliser);
        }
        return manager;
    }

    public String normalise(String jobPosition) throws Exception {

        String jobPositionNormalised = StringUtils.normaliseStringPosition(jobPosition).toLowerCase();

        TreeMap<Integer, Set<Job>> mapScores = normaliser.normalize(jobPositionNormalised, jobsPosition);

        Job bestAnswer;
        ArrayList<Job> jobWithBiggestScores = new ArrayList<>(mapScores.lastEntry().getValue());
        if(jobWithBiggestScores.size() > 1) {
            System.out.println(String.format("We found %d possibilities that matches %s. Chose the most correct one:", jobWithBiggestScores.size(), jobPositionNormalised));
            for (int i = 0; i < jobWithBiggestScores.size(); i++) {
                System.out.println(String.format("%d. %s",(i + 1), jobWithBiggestScores.get(i).getJobTitle()));
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int input = Integer.parseInt(reader.readLine()) -1;

            bestAnswer = jobWithBiggestScores.get(input);
        } else {
            bestAnswer = jobWithBiggestScores.get(0);
        }

        Job jobFound = jobsPosition.stream().filter(job -> job.equals(bestAnswer)).findFirst().orElseThrow(() -> new Exception("Job Not Found"));
        jobFound.updateKeyWords(jobPositionNormalised.split(" "));
        jsonReader.updateJson(jobsPosition);
        return bestAnswer.getJobTitle();
    }
}
