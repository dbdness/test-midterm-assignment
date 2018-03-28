package testex;

import com.jayway.restassured.response.ExtractableResponse;

import static com.jayway.restassured.RestAssured.given;

public class EducationalProgrammingJoke implements Joke {
    String reference;

    @Override
    public String getJoke() {
        try {
            ExtractableResponse res =  given().get("http://jokes-plaul.rhcloud.com/api/joke").then().extract();
            String joke = res.path("joke");
            reference = res.path("reference");
            return joke;
        } catch (Exception e) {
            return "{Joke couldn't be fetched}";
        }
    }
}
