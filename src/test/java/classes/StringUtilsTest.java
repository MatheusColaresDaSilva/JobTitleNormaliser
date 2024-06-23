package classes;

import com.classes.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class StringUtilsTest {

    Set<String> keywords;
    Set<String> nullSet;

    @Before
    public void setUp() {
        keywords = new HashSet<>();
        keywords.add("developer");
        keywords.add("engineer");
        keywords.add("programmer");
    }

    @Test
    public void testWhenKeyWordsNullThenReturnFalse() {
        String word = "developer";
        assertFalse(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(nullSet, word));
    }

    @Test
    public void testWhenAWordMatchesPerfectlyThenShouldReturnTrue() {
        String word = "developer";
        assertTrue(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(keywords, word));
    }

    @Test
    public void testWhenAWordMatchesAlmostPerfectlyThenShouldReturnTrue() {
        String word = "developes";
        assertTrue(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(keywords, word));
    }

    @Test
    public void testWhenAWordDoesNotMatchesMoreThan90PercentThenShouldReturnTrue() {
        String word = "desenvolvedor";
        assertFalse(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(keywords, word));
    }

    @Test
    public void testWhenMatches100PercentThenReturnTrue() {
        String s1 = "developer";
        String s2 = "developer";
        assertTrue(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(s1, s2));
    }

    @Test
    public void testWhenMatches90PercentThenReturnTrue() {
        String s1 = "developer";
        String s2 = "develop";
        assertTrue(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(s1, s2));
    }

    @Test
    public void testWhenMatchesLess90PercentThenReturnFalse() {
        String s1 = "developer";
        String s2 = "desenvolvedor";
        assertFalse(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(s1, s2));
    }

    @Test
    public void testWhenAtLeastOneStringIsNullThenReturnFalse() {
        String s1 = null;
        String s2 = "desenvolvedor";
        assertFalse(StringUtils.checkKeyWordWithMoreThanNinetyPerctSimilar(s1, s2));
    }

    @Test
    public void testDiffentsTypesOfInputs() {
        String s1 = "DEVELOPER";
        String test1 = StringUtils.normaliseStringPosition(s1);
        assertEquals(test1, "developer");

        s1 = "Dev.!eloper            ";
        String test2 = StringUtils.normaliseStringPosition(s1);
        assertEquals(test2, "developer");

        s1 = "            DEVELOPER            ";
        String test3 = StringUtils.normaliseStringPosition(s1);
        assertEquals(test3, "developer");

        s1 = "            DEVELOPER     java     ##  ";
        String test4 = StringUtils.normaliseStringPosition(s1);
        assertEquals(test4, "developer java");

        s1 = null;
        String test5 = StringUtils.normaliseStringPosition(s1);
        assertEquals(test5, "");

    }
}
