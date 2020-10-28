package jdk8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-14</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class DateTest {
    @Test
    public void LocalDateTest() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());                //2013-05-15
        System.out.println(localDate.getDayOfWeek().toString()); //WEDNESDAY
        System.out.println(localDate.getDayOfMonth());           //15
        System.out.println(localDate.getDayOfYear());            //135
        System.out.println(localDate.isLeapYear());              //false
        System.out.println(localDate.plusDays(12).toString());   //2013-05-27

    }

    @Test
    public void LocaDateTimeTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDateTime parse = LocalDateTime.parse("2007-12-03T10:15:30");

        LocalDateTime parse2 = LocalDateTime.parse("1986-04-08 12:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println("now=>" + now);
        System.out.println("ofInstant=>" + ofInstant);
        System.out.println("parse=>" + parse);
        System.out.println("parse2=>" + parse2);


    }

    @Test
    public void LocaDateTimeTest2() {
        LocalTime localTime = LocalTime.of(12, 20);
        System.out.println(localTime.toString());    //12:20
        System.out.println(localTime.getHour());     //12
        System.out.println(localTime.getMinute());   //20
        System.out.println(localTime.getSecond());   //0
        System.out.println(localTime.MIDNIGHT);      //00:00
        System.out.println(localTime.NOON);          //12:00

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toString());      //2013-05-15T10:01:14.911
        System.out.println(localDateTime.getDayOfMonth()); //15
        System.out.println(localDateTime.getHour());       //10
        System.out.println(localDateTime.getNano());       //911000000

    }

    @Test
    public void parseDate() {
        String armisticeDate = "2016-04-04";

        LocalDate aLD = LocalDate.parse(armisticeDate);
        System.out.println("Date: " + aLD);

        String armisticeDateTime = "2007-12-03T10:15:30";

        LocalDateTime aLDT = LocalDateTime.parse(armisticeDateTime);
        System.out.println("Date/Time: " + aLDT);


    }

    @Test
    public void InstantTest() {
        Instant instant = Instant.now();
        System.out.println(instant.toString());                                 //2013-05-15T05:20:08.145Z
        System.out.println(instant.plus(Duration.ofMillis(5000)).toString());   //2013-05-15T05:20:13.145Z
        System.out.println(instant.minus(Duration.ofMillis(5000)).toString());  //2013-05-15T05:20:03.145Z
        System.out.println(instant.minusSeconds(10).toString());                //2013-05-15T05:19:58.145Z
    }

    @Test
    public void DurationTest() {
        Duration duration = Duration.ofMillis(5000);
        System.out.println(duration.toString());     //PT5S

        duration = Duration.ofSeconds(60);
        System.out.println(duration.toString());     //PT1M

        duration = Duration.ofMinutes(10);
        System.out.println(duration.toString());     //PT10M

        duration = Duration.ofHours(2);
        System.out.println(duration.toString());     //PT2H

        duration = Duration.between(Instant.now(), Instant.now().plus(Duration.ofMinutes(10)));
        System.out.println(duration.toString());  //PT10M
    }


    @Test
    public void PeriodTest() {
        Period period = Period.ofDays(6);
        System.out.println(period.toString());    //P6D

        period = Period.ofMonths(6);
        System.out.println(period.toString());    //P6M

        period = Period.between(LocalDate.now(),
                LocalDate.now().plusDays(60));
        System.out.println(period.toString());   //P1M29D
    }

    //How do you find last day of the month? Or the next working day?
    @Test
    public void Dateadjusters() {
        LocalDate date = LocalDate.of(2013, Month.MAY, 15);                     //Today

        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(endOfMonth.toString());                              //2013-05-31

        LocalDate nextTue = date.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        System.out.println(nextTue.toString());


        LocalDate now = LocalDate.now();
        LocalDate nextDay = now.withMonth(Month.MAY.getValue()).with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        System.out.println(nextDay.toString());

    }

    @Test
    public void CreatingDateObjects() {
//Builder pattern used to make date object
        OffsetDateTime date1 = Year.of(2013)
                .atMonth(Month.MAY).atDay(15)
                .atTime(0, 0)
                .atOffset(ZoneOffset.of("+03:00"));
        System.out.println(date1);                                     //2013-05-15T00:00+03:00

//factory method used to make date object
        OffsetDateTime date2 = OffsetDateTime.
                of(2013, 5, 15, 0, 0, 0, 0, ZoneOffset.of("+03:00"));
        System.out.println(date2);                                      //2013-05-15T00:00+03:00
    }


    @Test
    public void ClockTest() {
        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock);                      //SystemClock[Asia/Calcutta]
        System.out.println(clock.instant().toString()); //2013-05-15T06:36:33.837Z
        System.out.println(clock.getZone());            //Asia/Calcutta

        Clock anotherClock = Clock.system(ZoneId.of("Europe/Tiraspol"));
        System.out.println(anotherClock);                       //SystemClock[Europe/Tiraspol]
        System.out.println(anotherClock.instant().toString());  //2013-05-15T06:36:33.857Z
        System.out.println(anotherClock.getZone());             //Europe/Tiraspol

        Clock forwardedClock = Clock.tick(anotherClock, Duration.ofSeconds(600));
        System.out.println(forwardedClock.instant().toString());  //2013-05-15T06:30Z
    }


}
