package com.classes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Position implements Job {
    /*This class is a representation from the JSON file, or a table in a NoSQL DB if I need to change in the future*/


    private String jobTitle;
    private Set<String> keywords;

    public Position(String jobTitle, Set<String> keywords) {
        this.jobTitle = jobTitle;
        this.keywords = keywords;
    }

    @Override
    public void updateKeyWords(String[] keywords) {
        this.keywords.addAll(Arrays.asList(keywords));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Job job = (Job) obj;
        return StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(jobTitle, job.getJobTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobTitle);
    }

}
