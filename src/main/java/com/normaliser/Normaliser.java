package com.normaliser;


import com.classes.Job;
import com.classes.Position;
import com.classes.StringUtils;
import com.exceptions.StringNotSuitableForNormalizationException;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.classes.StringUtils.*;

public class Normaliser {

    public  TreeMap<Integer, Set<Job>> normalize(String jobPositionNormalised, List<Job> jobsPosition) {
        if(StringUtils.isStringNullOrEmpty(jobPositionNormalised)) {
            throw new StringNotSuitableForNormalizationException();
        }

        return matchKeywords(jobPositionNormalised, jobsPosition);
    }

    private TreeMap<Integer, Set<Job>> matchKeywords(String jobPositionNormalised, List<Job> jobsPosition) {
        String[] arrayTokens = jobPositionNormalised.split(" ");

        TreeMap<Integer, Set<Job>> mapScores = new TreeMap<>();
        int i = 0;
        Thread thread = null;
        try{
            for (Job job : jobsPosition) {

                    int taskNumber = i++;
                    thread = new Thread(() -> {
                        System.out.println("Task " + taskNumber + " is running.");
                        int score = calculateQualityScore(job, jobPositionNormalised, arrayTokens, job.getKeywords());
                        addElement(mapScores, score, job);
                        System.out.println("Task " + taskNumber + " has finished.");
                    });
                    thread.start();
            }

          thread.join();
        } catch (InterruptedException | NullPointerException  e) {
            addElement(mapScores, -1, new Position());
        }

        return mapScores;
    }

    public static void addElement(TreeMap<Integer, Set<Job>> map, Integer key, Job job) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<>());
        }
        map.get(key).add(job);
    }

    private int calculateQualityScore(Job jobsPosition, String jobTitle, String[] arrayTokens, Set<String> keywords) {
        int score = 0;

        /*If the matcher with the title is more the 90%, the scroe increase 100 poins*/
        if (checkKeyWordWithMoreThanNinetyPerctSimilar(jobsPosition.getJobTitle().toLowerCase(), jobTitle)) {
            score+=100;
        }

        /*Even though, I will check in the keywords to have sure*/
        for (String word : arrayTokens) {
            if (checkKeyWordWithMoreThanNinetyPerctSimilar(keywords, word)) {
                score++;
            }
        }
        return score;
    }
}
