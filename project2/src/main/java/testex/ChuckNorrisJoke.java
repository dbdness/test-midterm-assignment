package testex;

import static com.jayway.restassured.RestAssured.given;

public class ChuckNorrisJoke implements Joke{
    private String reference;

    @Override
    public String getJoke() {
        try {
            String joke = given().get("http://api.icndb.com/jokes/random").path("value.joke");
            reference = "http://api.icndb.com";
            return joke;
        } catch (Exception e) {
            return "{Joke couldn't be fetched}";
        }
    }
}
