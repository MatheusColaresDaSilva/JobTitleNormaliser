package com.normaliser;

import com.classes.Job;
import com.classes.Position;
import com.exceptions.JobNotFoundException;
import com.exceptions.StringNotSuitableForNormalizationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ManagerTest {

    @Mock
    private JsonReader jsonReader;

    @Mock
    private Normaliser normaliser;

    @InjectMocks
    private Manager manager;

    private List<Job> jobsPosition;

    private TreeMap<Integer, Set<Job>> mapScores;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        jobsPosition = new ArrayList<>();
        jobsPosition.add(new Position("Software Engineer", Stream.of("java", "python", "c#", "programmer", "engineer").collect(Collectors.toSet())));
        jobsPosition.add(new Position("Architect", Stream.of("architect", "constructor").collect(Collectors.toSet())));
        jobsPosition.add(new Position("Quantity Surveyor", Stream.of("quantity", "surveyor").collect(Collectors.toSet())));

        verify(jsonReader).read();

        mapScores = new TreeMap<>();

        Set<Job> jobSet1 = new HashSet<>();
        jobSet1.add(new Position("Quantity Surveyor", Stream.of("quantity", "surveyor").collect(Collectors.toSet())));
        jobSet1.add(new Position("Architect", Stream.of("architect", "constructor").collect(Collectors.toSet())));
        mapScores.put(0, jobSet1);

        Set<Job> jobSet2 = new HashSet<>();
        jobSet2.add(new Position("Software Engineer", Stream.of("java", "python", "c#", "programmer", "engineer").collect(Collectors.toSet())));
        mapScores.put(2, jobSet2);

    }

    @Test
    public void testWhenMapScoresReturnEmptyTreeReturnNull() {
        when(normaliser.normalize(anyString(), anyList())).thenReturn(new TreeMap<>());

        Set<Job> result = manager.normalise("Java Developer");

        verify(normaliser).normalize(eq("java developer"), anyList());
        assertNull(result);
    }


    @Test
    public void testWhenMapScoresReturnwwNotEmptyTreeReturnTheLastPostition() {

        when(normaliser.normalize(anyString(), anyList())).thenReturn(mapScores);

        Set<Job> result = manager.normalise("Java Programmer");

        verify(normaliser, times(1)).normalize(eq("java programmer"), anyList());
        assertNotNull(result);
        assertEquals(1, result.size());

        Job job = result.iterator().next();
        assertEquals("Software Engineer", job.getJobTitle());

    }

    @Test
    public void testWhenJobNotFoundInTheListThenThroeExcepion() throws Exception {
        Field jobRefle = manager.getClass().getDeclaredField("jobsPosition");
        jobRefle.setAccessible(true);
        jobRefle.set(manager, jobsPosition);

        Job jobFound = new Position("NOT EXISTE JOB", Stream.of("java", "python", "c#", "programmer", "engineer").collect(Collectors.toSet()));

        Exception exception = assertThrows(JobNotFoundException.class, () -> {
            manager.saveBestAnswerInMemory(jobFound);
        });

        String expectedMessage = "Job Not Found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testWhenJobFoundInTheListThenShouldAddkeyWordInMemory() throws Exception {
        Field jobRefle = manager.getClass().getDeclaredField("jobsPosition");
        jobRefle.setAccessible(true);
        jobRefle.set(manager, jobsPosition);

        Job jobFound = new Position("Software Engineer", Stream.of("java", "python", "c#", "programmer", "engineer").collect(Collectors.toSet()));

        manager.saveBestAnswerInMemory(jobFound);
        verify(jsonReader, times(1)).updateJson(anyList());

    }
}
