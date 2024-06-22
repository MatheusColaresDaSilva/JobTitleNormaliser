package com.classes;

import java.util.Set;

public interface Job {

    String getJobTitle();

    Set<String> getKeywords();

    void updateKeyWords(String[] keywords);
}
