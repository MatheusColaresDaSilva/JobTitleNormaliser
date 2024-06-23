package com.normaliser;

import com.classes.Job;
import com.classes.Position;
import com.classes.StringUtils;
import com.exceptions.StringNotSuitableForNormalizationException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


public class NormaliserTest {

    private Normaliser normaliser;
    private List<Job> jobsPosition;

    @Before
    public void setUp() {

        jobsPosition = new ArrayList<>();
        jobsPosition.add(new Position("Software Engineer", Stream.of("java", "python", "c#", "programmer", "engineer").collect(Collectors.toSet())));
        jobsPosition.add(new Position("Architect", Stream.of("architect", "constructor").collect(Collectors.toSet())));
        jobsPosition.add(new Position("Quantity Surveyor", Stream.of("quantity", "surveyor").collect(Collectors.toSet())));

        normaliser = new Normaliser();
    }

    @Test
    public void testWhenPositionPerfectMatchThenReturnJustOnePosition() {
        String jobPositionNormalise = StringUtils.normaliseStringPosition("Java engineer");
        TreeMap<Integer, Set<Job>> result = normaliser.normalize(jobPositionNormalise, jobsPosition);

        assertEquals(2, result.size(), "This size should be 2, because we have the positions with 0 score and 2 score");

        Integer key = result.lastKey();
        assertEquals(2, key, "Get the last, because the treeMap is ordered, thus the lastkey is the highest scored positions");

        Set<Job> jobSet = result.get(key);
        assertNotNull(jobSet, "Check Set not null");
        assertEquals(1, jobSet.size(), "In this case, size should be one, because just Sof.Engineer metches 2 keywords");

        Job job = jobSet.iterator().next();
        assertEquals("Software Engineer", job.getJobTitle(), "The job title most relevant shoul be 'Software Engineer'");

    }

    @Test
    public void testWhenPositionDoNotPerfectMatchThenReturnMoreThanOnePosition() {
        String jobPositionNormalise = StringUtils.normaliseStringPosition("Java Architect");
        TreeMap<Integer, Set<Job>> result = normaliser.normalize(jobPositionNormalise, jobsPosition);

        assertEquals(2, result.size(), "This size should be 2, because we have the positions with 0 score and 100 score");

        Integer key = result.lastKey();
        assertEquals(1, key, "Get the last, because the treeMap is ordered, thus the lastkey is the highest scored positions");

        Set<Job> jobSet = result.get(key);
        assertNotNull(jobSet, "Check Set not null");
        assertEquals(2, jobSet.size(), "In this case, size should be 2, because the Sof.Engineer and Architect matches 1 keywords, JAVA and Architect");

        assertThat(jobSet, containsInAnyOrder(
                hasProperty("jobTitle", is("Software Engineer")),
                hasProperty("jobTitle", is("Architect"))
        ));
    }

    @Test
    public void testWhenPositionPerfectMatchWIthJobTiltleThenReturnOnePosition() {
        jobsPosition = new ArrayList<>();
        jobsPosition.add(new Position("Software Engineer", Stream.of("java", "python", "c#", "programmer", "engineer", "architect").collect(Collectors.toSet())));
        jobsPosition.add(new Position("Architect", Stream.of("architect", "constructor").collect(Collectors.toSet())));
        jobsPosition.add(new Position("Quantity Surveyor", Stream.of("quantity", "surveyor").collect(Collectors.toSet())));

        String jobPositionNormalise = StringUtils.normaliseStringPosition("Architect");
        TreeMap<Integer, Set<Job>> result = normaliser.normalize(jobPositionNormalise, jobsPosition);

        assertEquals(3, result.size(), "This size should be 3, because we have the positions with 0 score, 1 score and 101 score");

        Integer key = result.lastKey();
        assertEquals(101, key, "Get the last, because the treeMap is ordered, thus the lastkey is the highest scored positions");

        Set<Job> jobSet = result.get(key);
        assertNotNull(jobSet, "Check Set not null");
        assertEquals(1, jobSet.size(), "In this case, size should be 2, because just Architect matches percetely with the job title");

        Job job = jobSet.iterator().next();
        assertEquals("Architect", job.getJobTitle());
    }

    @Test
    public void testWhenStringEmptyThenThrowException() {
        String jobPositionNormalise = StringUtils.normaliseStringPosition(" ");

        Exception exception = assertThrows(StringNotSuitableForNormalizationException.class, () -> {
            normaliser.normalize(jobPositionNormalise, jobsPosition);
        });

        String expectedMessage = "Job title is not suitable for Normalization";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void whenStringNullThenThrowException() {
        String jobPositionNormalise = StringUtils.normaliseStringPosition(null);

        Exception exception = assertThrows(StringNotSuitableForNormalizationException.class, () -> {
            normaliser.normalize(jobPositionNormalise, jobsPosition);
        });

        String expectedMessage = "Job title is not suitable for Normalization";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}
