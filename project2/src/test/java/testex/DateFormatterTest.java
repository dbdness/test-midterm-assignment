package testex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateFormatterTest {
    private DateFormatter dateFormatter;
    private Calendar calendar;

    @BeforeEach
    void init() throws JokeException {
        dateFormatter = new DateFormatter("Europe/Copenhagen");
        calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JANUARY, 3, 15, 30);
    }

    //Our own time zone should return the exact set instance in the correct format.
    @Test
    void ourTimeZoneTest() throws JokeException {
        Date date = calendar.getTime();
        String formatted = dateFormatter.getFormattedDate(date);
        assertThat(formatted, is("03 Jan 2018 03:30 PM"));
    }

    //Kiev, for example, is one hour ahead of our time zone.
    @Test
    void otherTimeZoneTest() throws JokeException {
        String zone = "Europe/Kiev";
        dateFormatter = new DateFormatter(zone);
        Date date = calendar.getTime();
        String formatted = dateFormatter.getFormattedDate(date);
        assertThat(formatted, is("03 Jan 2018 04:30 PM"));
    }

    @Test
    void invalidDateExceptionTest() {
        String invalid = "invalidZone";
        assertThrows(JokeException.class, () -> dateFormatter = new DateFormatter(invalid));
    }

}