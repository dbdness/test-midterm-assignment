package testex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This is a "normal" test class for {@link JokeFetcher} WITHOUT the use of Mocks.
 * The idea is to show what tests I would've run in a scenario where it didn't make sense to use Mocks.
 * <p>
 * See {@link JokeFetcherMockTest} for test cases USING mocks.
 */
class JokeFetcherTest {

    private JokeFetcher jokeFetcher;
    private JokeFactory jokeFactory;

    private Date testDate;

    @BeforeEach
    void init() throws JokeException {
        jokeFactory = new JokeFactory();
        DateFormatter dateFormatter = new DateFormatter("Europe/Copenhagen");
        jokeFetcher = new JokeFetcher(jokeFactory, dateFormatter);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JANUARY, 3, 15, 30);
        testDate = calendar.getTime();
    }

    @Test
    void getAvailableTypesTest() {
        List<String> availableTypes = jokeFactory.getAvailableTypes();
        assertThat(availableTypes, contains("eduprog", "chucknorris", "moma", "tambal"));
        assertThat(availableTypes, hasSize(4));
    }

    @ParameterizedTest
    @ValueSource(strings = {"eduprog", "chucknorris", "moma", "tambal"})
    void validStringTest(String validToken) {
        Boolean valid = jokeFetcher.isStringValid(validToken);
        assertThat(valid, is(true));
    }

    @Test
    void invalidStringTest() {
        String invalid = "invalidToken";
        Boolean isValid = jokeFetcher.isStringValid(invalid);
        assertThat(isValid, is(false));
    }

    @Test
    void getJokesTest() throws JokeException {
        Jokes jokes = jokeFetcher.getJokes("chucknorris,chucknorris", testDate);

        assertThat(jokes.getJokes(), hasSize(2));
        assertThat(jokes.getTimeZoneString(), is("03 Jan 2018 03:30 PM"));
    }

    @Test
    void getJokesExceptionTest() {
        String invalidFormat = "invalidJoke";
        assertThrows(JokeException.class, () -> jokeFetcher.getJokes(invalidFormat, testDate));
    }


}