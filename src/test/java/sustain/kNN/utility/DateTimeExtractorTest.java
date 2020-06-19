package sustain.kNN.utility;

import org.junit.Test;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.Assert.*;

/**
 * Created by laksheenmendis on 6/19/20 at 3:58 PM
 */
public class DateTimeExtractorTest {

    String testTimestamp = "2015-01-01 14:30";
    String JAN_TIMESTAMP = "2015-01-01 14:30";
    String FEB_TIMESTAMP = "2015-02-01 14:30";
    String MAR_TIMESTAMP = "2015-03-01 14:30";
    String APR_TIMESTAMP = "2015-04-01 14:30";
    String MAY_TIMESTAMP = "2015-05-01 14:30";
    String JUN_TIMESTAMP = "2015-06-01 14:30";
    String JUL_TIMESTAMP = "2015-07-01 14:30";
    String AUG_TIMESTAMP = "2015-08-01 14:30";
    String SEP_TIMESTAMP = "2015-09-01 14:30";
    String OCT_TIMESTAMP = "2015-10-01 14:30";
    String NOV_TIMESTAMP = "2015-11-01 14:30";
    String DEC_TIMESTAMP = "2015-12-01 14:30";
    String wrongTimestamp1 = "2016-01-29";
    String wrongTimestamp2 = "2016-01 14:30";
    String wrongTimestamp3 = "2016-01 14";

    @Test
    public void getYearFromTimestamp() {
        assertEquals(2015, DateTimeExtractor.getYearFromTimestamp(testTimestamp));
    }

    @Test
    public void getMonthFromTimestamp() {
        assertEquals(Month.JANUARY, DateTimeExtractor.getMonthFromTimestamp(JAN_TIMESTAMP));
        assertEquals(Month.FEBRUARY, DateTimeExtractor.getMonthFromTimestamp(FEB_TIMESTAMP));
        assertEquals(Month.MARCH, DateTimeExtractor.getMonthFromTimestamp(MAR_TIMESTAMP));
        assertEquals(Month.APRIL, DateTimeExtractor.getMonthFromTimestamp(APR_TIMESTAMP));
        assertEquals(Month.MAY, DateTimeExtractor.getMonthFromTimestamp(MAY_TIMESTAMP));
        assertEquals(Month.JUNE, DateTimeExtractor.getMonthFromTimestamp(JUN_TIMESTAMP));
        assertEquals(Month.JULY, DateTimeExtractor.getMonthFromTimestamp(JUL_TIMESTAMP));
        assertEquals(Month.AUGUST, DateTimeExtractor.getMonthFromTimestamp(AUG_TIMESTAMP));
        assertEquals(Month.SEPTEMBER, DateTimeExtractor.getMonthFromTimestamp(SEP_TIMESTAMP));
        assertEquals(Month.OCTOBER, DateTimeExtractor.getMonthFromTimestamp(OCT_TIMESTAMP));
        assertEquals(Month.NOVEMBER, DateTimeExtractor.getMonthFromTimestamp(NOV_TIMESTAMP));
        assertEquals(Month.DECEMBER, DateTimeExtractor.getMonthFromTimestamp(DEC_TIMESTAMP));
    }

    @Test
    public void getDateFromTimestamp() {
        assertEquals(1, DateTimeExtractor.getDateFromTimestamp(testTimestamp));
    }

    @Test
    public void getHourFromTimestamp() {
        assertEquals(14, DateTimeExtractor.getHourFromTimestamp(testTimestamp));
    }

    @Test
    public void getMinuteFromTimestamp() {
        assertEquals(30, DateTimeExtractor.getMinuteFromTimestamp(testTimestamp));
    }

    @Test
    public void getLocalDateTime() {
        assertEquals(LocalDateTime.of(2015,1,1,14,30), DateTimeExtractor.getLocalDateTime(testTimestamp));
    }
}