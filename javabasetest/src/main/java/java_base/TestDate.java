package java_base;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * @author kled
 * @version $Id: TestDate.java, v 0.1 2019-02-20 19:26:35 kled Exp $
 */
public class TestDate {
    public static void main(String[] args) throws InterruptedException {
        // Date <==> Instant <==> LocalDateTime

        System.out.println("Date:" + new Date());  //Wed Feb 20 20:35:17 CST 2019   CST: 美国、澳大利亚、古巴或中国的标准时间时间

        System.out.println("Instant:" + Instant.now()); // 默认2019-02-20T12:41:37.826Z   T:24小时制  Z: UTC(协调世界时-原子钟)时间
        System.out.println(Instant.now().getNano()); //当前纳秒数余数？< 10^9（1s）

        System.out.println("New_York LocalDateTime:" + LocalDateTime.now(ZoneId.of("America/New_York"))); //2019-02-20T07:41:37.962 西五区 -5
        System.out.println("LocalDateTime:" + LocalDateTime.now()); //默认当地时间-Asia/Shanghai时间 2019-02-20T20:41:37.965 东八区 + 8
        System.out.println("New_York LocalDateTime to instant:" + LocalDateTime.now().atZone(ZoneId.of("America/New_York")).toInstant());
        System.out.println("ZonedDateTime:" + ZonedDateTime.now()); //2019-02-20T20:48:19.490+08:00[Asia/Shanghai] 带时区及时差

        System.out.println("default ZoneId:" + ZoneId.systemDefault()); //默认时区-Asia/Shanghai
        System.out.println("available ZoneId:" + ZoneId.getAvailableZoneIds()); //默认时区-Asia/Shanghai

        // Clock
        // SystemClock.instant 时间戳时钟代表UTC？？
        System.out.println("UTC Clock:" + Clock.systemUTC());
        System.out.println("Europe/Paris Clock:" + Clock.system(ZoneId.of("Europe/Paris"))); // 巴黎时钟
        System.out.println(Clock.tickMinutes(ZoneId.of("America/New_York")).instant()); // 2019-02-21T02:28:00Z 只保留分钟
        System.out.println(Clock.tickSeconds(ZoneId.of("America/New_York")).instant()); // 2019-02-21T02:28:00Z 只保留秒
        System.out.println("default[Asia/Shanghai] Clock:" + Clock.systemDefaultZone().instant());

        // LocalDate
        LocalDate localDate = LocalDate.of(2017, 1, 4);     // 初始化一个日期：2017-01-04
        int year = localDate.getYear();                     // 年份：2017
        Month month = localDate.getMonth();                 // 月份：JANUARY
        int dayOfMonth = localDate.getDayOfMonth();         // 月份中的第几天：4
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();     // 一周的第几天：WEDNESDAY
        int length = localDate.lengthOfMonth();             // 月份的天数：31
        boolean leapYear = localDate.isLeapYear();          // 是否为闰年：false
        LocalDate date6 = localDate.plus(5, ChronoUnit.DAYS);    // 增加5天
        LocalDate date7 = localDate.with(nextOrSame(DayOfWeek.SUNDAY)); // 接收一个TemporalAdjuster参数，可以使我们更加灵活的调整日期
        //@FunctionalInterface
        //public interface TemporalAdjuster {
        //    Temporal adjustInto(Temporal temporal);
        //}
        LocalDate date = LocalDate.parse("2017-01-05", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime dateTime1 = LocalDateTime.parse("2017-01-05 12:30:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        // LocalTime
        LocalTime localTime = LocalTime.of(17, 23, 52);     // 初始化一个时间：17:23:52
        int hour = localTime.getHour();                     // 时：17
        int minute = localTime.getMinute();                 // 分：23
        int second = localTime.getSecond();                 // 秒：52

        // LocalDateTime
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()); //Instant -> LocalDateTime
        LocalDateTime ldt1 = LocalDateTime.of(2017, Month.JANUARY, 4, 17, 23, 52);
        LocalDate localDate1 = LocalDate.of(2017, Month.JANUARY, 4);
        LocalTime localTime1 = LocalTime.of(17, 23, 52);
        LocalDateTime ldt2 = localDate1.atTime(localTime1); // LocalDate + LocalTime

        // Instant: 表示一个时间戳（或者说是一个时间点）
        Instant instant5 = new Date().toInstant(); //Date -> Instant
        Instant instant4 = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(); //localDate -> Instant
        Instant now1 = Instant.now();
        now1.truncatedTo(ChronoUnit.HOURS); //时间截断  9:10:10 => 9:00:00
        now1.until(now1.truncatedTo(ChronoUnit.MINUTES), ChronoUnit.MINUTES); //根据指定的单位计算到另一个瞬间的时间量。
        Instant instant1 = Instant.ofEpochSecond(1200); //距离UTC 1200s
        Instant instant2 = Instant.ofEpochMilli(1000); //距离UTC 1000ms
        Instant instant3 = Instant.ofEpochSecond(1000, 1000); //距离UTC 1000s, 1000ns


        // Duration: 表示一个时间段
        LocalDateTime from = LocalDateTime.of(2017, Month.JANUARY, 5, 10, 7, 0);    // 2017-01-05 10:07:00
        LocalDateTime to = LocalDateTime.of(2017, Month.FEBRUARY, 5, 10, 7, 0);     // 2017-02-05 10:07:00
        Duration duration = Duration.between(from, to);     // 表示从 2017-01-05 10:07:00 到 2017-02-05 10:07:00 这段时间
        long days = duration.toDays();              // 这段时间的总天数
        long hours = duration.toHours();            // 这段时间的小时数
        long minutes = duration.toMinutes();        // 这段时间的分钟数
        long seconds = duration.getSeconds();       // 这段时间的秒数
        long milliSeconds = duration.toMillis();    // 这段时间的毫秒数
        long nanoSeconds = duration.toNanos();      // 这段时间的纳秒数
        Duration duration1 = Duration.of(5, ChronoUnit.DAYS);       // 5天
        Duration duration2 = Duration.of(1000, ChronoUnit.MILLIS);  // 1000毫秒
        duration2.toMillis();  //转化为纳秒/毫秒/秒/分/时/天

        // Period: 在概念上和Duration类似，区别在于Period是以年月日来衡量一个时间段
        Period period = Period.of(2, 3, 6);
        Period period1 = Period.between(LocalDate.of(2017, 1, 5), LocalDate.of(2017, 2, 5));
        period1.getDays(); //day/years/mouths/weeks

        // ChronoUnit: 在单个时间单位内测量一段时间，例如天数或秒等
        long daysDiff = ChronoUnit.DAYS.between(LocalDate.of(2017, 1, 5), LocalDate.of(2017, 2, 5));

        // DateTimeFormatter  parse/format
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("BASIC_ISO_DATE:" + dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));    // 20170105
        System.out.println("ISO_LOCAL_DATE:" + dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));    // 2017-01-05
        System.out.println("ISO_LOCAL_TIME:" + dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME));    // 14:20:16.998
        System.out.println("self-Defined:" + dateTime
                .format(DateTimeFormatter.ofPattern("今天是：YYYY年 MMMM DD日 E", Locale.CHINESE))); // 今天是：2017年 一月 05日 星期四

        // ZoneId: LocalDateTime + ZoneId = ZoneDateTime 时区
        ZoneId shanghaiZoneId = ZoneId.of("Asia/Shanghai");
        ZoneId systemZoneId = ZoneId.systemDefault();
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, shanghaiZoneId);

        // ZoneOffset: LocalDateTime + ZoneOffset = OffsetDateTime 时区
        ZoneOffset zoneOffset = ZoneOffset.of("+09:00");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, zoneOffset);
    }
}
