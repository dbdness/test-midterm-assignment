package testex;

import static com.jayway.restassured.RestAssured.given;

public class YoMommaJoke implements Joke {
    String reference;

    @Override
    public String getJoke() {
        try {
            String joke = given().get("http://api.yomomma.info/").andReturn().jsonPath().getString("joke");
            reference = "http://api.yomomma.info/";
            return joke;
        } catch (Exception e) {
            return "{Joke couldn't be fetched}";
        }
    }
}
