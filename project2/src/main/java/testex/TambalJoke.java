package testex;

import static com.jayway.restassured.RestAssured.given;

public class TambalJoke implements Joke {
    String reference;

    @Override
    public String getJoke() {
        try {
            String joke = given().get("http://tambal.azurewebsites.net/joke/random").path("joke");
            reference = "http://tambal.azurewebsites.net/joke/random";
            return joke;
        } catch (Exception e) {
            return "{Joke couldn't be fetched}";
        }
    }
}
