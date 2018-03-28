package testex;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter {
    private String timeZone;

    /**
     *
     * @param timeZone Valid timezone per getAvailableIDs() in the {@link TimeZone} class.
     * @throws JokeException If the timeZone string is not a valid string
     */
    DateFormatter(String timeZone) throws JokeException {
        if (!Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone)) {
            throw new JokeException("Illegal Time Zone String");
        }
        this.timeZone = timeZone;
    }

    /**
     * Returns a formatted string representing the date specified, adjusted to the time zone string
     * passed in the class constructor.
     * @param date Date to be formatted according to the timezone.
     * @return Time Zone string formatted like ("dd MMM yyyy hh:mm aa") and adjusted to the provided
     * time zone
     */
    public String getFormattedDate(Date date) {
        String dateTimeFormat = "dd MMM yyyy hh:mm aa";
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateTimeFormat);
        simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleFormat.format(date);
    }

    /**
     * DO NOT TEST this function as part of the exercise.
     * It's included only to get a quick way of executing the code
     * Execute to see available time format strings and responses to calling the single (non-main) public method
     */
    public static void main(String[] args) throws JokeException {

        for (String str : TimeZone.getAvailableIDs()) {
            System.out.println(str);
        }

        //Executing our public method with a valid String:
        System.out.println(new DateFormatter("Europe/Kiev").getFormattedDate(new Date())); //Now

        System.out.println(new DateFormatter("ImNotLegal").getFormattedDate(new Date()));


    }

}
