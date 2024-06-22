package com.normaliser;


import com.classes.Job;
import java.util.*;
import static com.classes.StringUtils.*;

public class Normaliser {

    public  TreeMap<Integer, Set<Job>> normalize(String jobPositionNormalised, List<Job> jobsPosition) {
        return matchKeywords(jobPositionNormalised, jobsPosition);
    }

    private TreeMap<Integer, Set<Job>> matchKeywords(String jobPositionNormalised, List<Job> jobsPosition) {
        String[] arrayTokens = jobPositionNormalised.split(" ");

        TreeMap<Integer, Set<Job>> mapScores = new TreeMap<>();

        for (Job job : jobsPosition) {
            int score = calculateQualityScore(job, jobPositionNormalised,arrayTokens, job.getKeywords());
            addElement(mapScores, score, job);
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
