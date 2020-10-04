package localTest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author kled
 * @version $Id: TestInstant.java, v 0.1 2019-05-16 10:14:33 kled Exp $
 */
public class TestInstant {

    public static void main(String[] args) {
        LocalDateTime dateTime1 = LocalDateTime.parse("2019-05-21 07:29:56", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(LocalDateTime.now());

        System.out.println(Instant.now().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime());
        System.out.println(Instant.now().atZone(ZoneId.of("America/New_York")).toLocalDateTime());
    }
}
