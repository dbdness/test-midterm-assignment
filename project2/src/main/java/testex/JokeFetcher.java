
package testex;

import java.util.Date;

/**
 * Class used to fetch jokes from a number of external joke API's
 */
public class JokeFetcher {
    private JokeFactory jokeFactory;
    private DateFormatter dateFormatter;

    JokeFetcher(JokeFactory jokeFactory, DateFormatter dateFormatter) {
        this.jokeFactory = jokeFactory;
        this.dateFormatter = dateFormatter;
    }

    /**
     * Verifies whether a provided value is a valid string (contained in availableTypes)
     *
     * @param jokeTokens Example (with valid values only): "eduprog,chucknorris,chucknorris,moma,tambal"
     * @return true if the param was a valid value, otherwise false
     */
    boolean isStringValid(String jokeTokens) {
        String[] tokens = jokeTokens.split(",");
        for (String token : tokens) {
            if (!jokeFactory.getAvailableTypes().contains(token)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param jokesToFetch A comma separated string with values (contained in availableTypes) indicating the jokes
     *                     to fetch. Example: "eduprog,chucknorris,chucknorris,moma,tambal" will return five jokes (two chucknorris)
     * @throws JokeException Thrown if either of the two input arguments contains illegal values
     */
    private void checkInput(String jokesToFetch) throws JokeException {
        if (!isStringValid(jokesToFetch)) {
            throw new JokeException("Inputs (jokesToFetch) contain types not recognized");
        }
    }

    /**
     * Fetch jokes from external API's as given in the input string - jokesToFetch
     *
     * @param jokesToFetch A comma separated string with values (contained in availableTypes) indicating the jokes
     *                     to fetch. Example: "eduprog,chucknorris,chucknorris,moma,tambal" will return five jokes (two chucknorris)
     * @param date         Formatted date to attach to the joke.
     * @return A Jokes instance with the requested jokes + time zone adjusted string representing fetch time
     * (the jokes list can contain null values, if a server did not respond correctly)
     */
    Jokes getJokes(String jokesToFetch, Date date) throws JokeException {
        checkInput(jokesToFetch);

        Jokes jokes = new Jokes();

        for (Joke joke : jokeFactory.getJokes(jokesToFetch)) {
            jokes.addJoke(joke);
        }

        String timeZoneString = dateFormatter.getFormattedDate(date);
        jokes.setTimeZoneString(timeZoneString);

        return jokes;
    }

    /**
     * DO NOT TEST this function. It's included only to get a quick way of executing the code
     *
     * @param args
     */
    public static void main(String[] args) throws JokeException {
        DateFormatter dateFormatter = new DateFormatter("Europe/Copenhagen");
        JokeFactory jokeFactory = new JokeFactory();
        JokeFetcher jf = new JokeFetcher(jokeFactory, dateFormatter);
        Jokes jokes = jf.getJokes("eduprog,chucknorris,chucknorris,moma,tambal", new Date());
        jokes.getJokes().forEach((Joke) -> {
            System.out.println(Joke.getJoke());
        });
        System.out.println("Is String Valid: " + jf.isStringValid("edu_prog,xxx"));
    }
}
