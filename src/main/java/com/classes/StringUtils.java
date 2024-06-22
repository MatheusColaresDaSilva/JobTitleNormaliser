package com.classes;

import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.util.Set;

public abstract class StringUtils {

    public static boolean checkKeyWordWithMoreThanNinetyPerctSimilar(Set<String> keywords, String word) {
        return keywords.stream().anyMatch(s -> calculateSimilarity(s, word) > 0.9);
    }

    public static boolean checkKeyWordWithMoreThanNinetyPerctSimilar(String s1, String s2) {
        return calculateSimilarity(s1, s2) > 0.9;
    }

    public static double calculateSimilarity(String s1, String s2) {
        /*The JaroWinklerDistance is a algorithm that provide the similaroty between two words*/
        JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
        return 1.0 - jaroWinklerDistance.apply(s1, s2);
    }

    public static String normaliseStringPosition(String jobPosition) {
        /*The remove extras spaces and specials characteres*/
        return (jobPosition == null || jobPosition.isEmpty()) ? "" :  jobPosition.trim().replaceAll("[^a-zA-Z0-9\\s]", "");
    }
}
