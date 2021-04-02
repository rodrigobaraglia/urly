package com.rodrigobaraglia.urly;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DateSumTest {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final DateTimeFormatter dateFormat8 = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Test
    void testDateSum() {
        Date currentDate = new Date();
        System.out.println("date : " + dateFormat.format(currentDate));

        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("localDateTime : " + dateFormat8.format(localDateTime));

        //localDateTime = localDateTime.plusYears(1).plusMonths(1).plusDays(1);
        localDateTime = localDateTime.plusHours(1).plusMinutes(2).minusMinutes(1).plusSeconds(1);

        // convert LocalDateTime to date
        Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        long diffInMillies = Math.abs(currentDatePlusOneDay.getTime() - currentDate.getTime());
        long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        System.out.println(diffInMillies);
        System.out.println(diff);
        System.out.println("\nOutput : " + dateFormat.format(currentDatePlusOneDay));

    }

    @Test
    public void givenTwoDatesBeforeJava8_whenDifferentiating_thenWeGetSix()
            throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = sdf.parse("06/24/2017");
        Date secondDate = sdf.parse("06/30/2017");

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        System.out.println(diff);
        System.out.println(diffInMillies);
        assertEquals(6, diff);
    }

}
