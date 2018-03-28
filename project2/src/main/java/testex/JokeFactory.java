package testex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JokeFactory {
    /**
     * These are the valid types that can be used to indicate required jokeOlds
     * eduprog: Contains joke related to programming and education. API only returns a new value each hour
     * chucknorris: Fetch a chucknorris joke. Not always political correct ;-)
     * moma: Fetch a "MOMA" joke. Defenitely never political correct ;-)
     * tambal: Just random jokeOlds
     */
    private final List<String> availableTypes = Arrays.asList("eduprog", "chucknorris", "moma", "tambal");

    /**
     * The valid string values to use in a call to getJokeOlds(..)
     *
     * @return All the valid strings that can be used
     */
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    public List<Joke> getJokes(String jokeTypes) throws JokeException {
        String[] tokens = jokeTypes.split(",");
        List<Joke> jokes = new ArrayList<>();
        for (String token : tokens) {
            switch (token) {
                case "eduprog":
                    jokes.add(new EducationalProgrammingJoke());
                    break;
                case "chucknorris":
                    jokes.add(new ChuckNorrisJoke());
                    break;
                case "moma":
                    jokes.add(new YoMommaJoke());
                    break;
                case "tambal":
                    jokes.add(new TambalJoke());
                    break;
                default:
                    throw new JokeException(String.format("'%s' not recognized as a joke type.", token));
            }
        }
        return jokes;
    }
}
