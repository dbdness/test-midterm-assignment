package testex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This is a test class for {@link JokeFetcher} WITH the use of Mocks.
 * <p>
 * See {@link JokeFetcherTest} for test cases WITHOUT the use of Mocks.
 */
class JokeFetcherMockTest {
    private JokeFetcher jokeFetcher;
    private Date testDate;

    @Mock
    private JokeFactory jokeFactory;

    @Mock
    private ChuckNorrisJoke chuckNorrisJoke;

    @Mock
    private EducationalProgrammingJoke eduProgJoke;

    @Mock
    private TambalJoke tambalJoke;

    @Mock
    private YoMommaJoke yoMommaJoke;

    @Mock
    private DateFormatter dateFormatter;

    @BeforeEach
    void init() throws JokeException {
        MockitoAnnotations.initMocks(this);

        jokeFetcher = new JokeFetcher(jokeFactory, dateFormatter);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JANUARY, 3, 15, 30);
        testDate = calendar.getTime();

        List<Joke> jokes = new ArrayList<>();
        jokes.add(chuckNorrisJoke);
        jokes.add(eduProgJoke);
        jokes.add(tambalJoke);
        jokes.add(yoMommaJoke);

        when(jokeFactory.getAvailableTypes()).thenReturn(Arrays.asList("eduprog", "chucknorris", "moma", "tambal"));
        when(jokeFactory.getJokes("chucknorris,eduprog,tambal,moma")).thenReturn(jokes);
        when(dateFormatter.getFormattedDate(anyObject())).thenReturn("03 Jan 2018 03:30 PM");
    }

    @Test
    void correctDateFormatTest() throws JokeException {
        Jokes jokes = jokeFetcher.getJokes("chucknorris,moma", testDate);

        verify(dateFormatter).getFormattedDate(testDate);

        assertThat(jokes.getTimeZoneString(), is("03 Jan 2018 03:30 PM"));
    }

    @Test
    void jokeFactoryCallTest() throws JokeException {
        Jokes jokes = jokeFetcher.getJokes("chucknorris,tambal,tambal", testDate);

        verify(jokeFactory).getJokes("chucknorris,tambal,tambal");

        assertThat(jokes.getTimeZoneString(), is("03 Jan 2018 03:30 PM"));
    }

    @Test
    void getJokesTest() throws JokeException {
        List<Joke> jokes = jokeFetcher.getJokes("chucknorris,eduprog,tambal,moma", testDate).getJokes();
        assertThat(jokes, contains(chuckNorrisJoke, eduProgJoke, tambalJoke, yoMommaJoke));
    }
}
