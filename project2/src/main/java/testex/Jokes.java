package testex;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a number of Jokes and a string value containing a time zone adjusted string indicating
 * when the jokes was fetched
 */
public class Jokes {

  private ArrayList jokes = new ArrayList();
  private String timeZoneString;

  void addJoke(Joke joke) {
    jokes.add(joke);
  }

  public List<Joke> getJokes() {
    return jokes;
  }

  public void setTimeZoneString(String timeZoneString) {
    this.timeZoneString = timeZoneString;
  }

  public String getTimeZoneString() {
    return timeZoneString;
  }

}
