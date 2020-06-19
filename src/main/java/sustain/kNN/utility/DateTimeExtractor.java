package sustain.kNN.utility;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by laksheenmendis on 6/12/20 at 4:08 PM
 */
public class DateTimeExtractor {

    private static NumberFormatException numberFormatEx = new NumberFormatException("Timestamp is not in required format");

    /**
     * Extracts year from from a timestamp which is in following format
     * YYYY-MM-DD HH:mm
     * @param timestamp
     * @return
     */
    public static int getYearFromTimestamp(String timestamp) throws NumberFormatException {
        String [] arr = timestamp.split(" ");

        if(arr.length == 2)
        {
            String [] newArr = arr[0].split("-");
            if(newArr.length == 3)
            {
                return Integer.parseInt(newArr[0]);
            }
            throw numberFormatEx;
        }
        throw numberFormatEx;
    }

    /**
     * Extracts Month from from a timestamp which is in following format
     * YYYY-MM-DD HH:mm
     * @param timestamp
     * @return java.time.Month
     */
    public static Month getMonthFromTimestamp(String timestamp) throws NumberFormatException
    {
        String [] arr = timestamp.split(" ");

        if(arr.length == 2)
        {
            String [] newArr = arr[0].split("-");
            if(newArr.length == 3)
            {
                return getMonth(newArr[1]);
            }
            throw numberFormatEx;
        }
        throw numberFormatEx;
    }

    private static Month getMonth(String monthString)
    {
        Month month = null;
        switch (monthString)
        {
            case "01":
                month = Month.JANUARY;
                break;
            case "02":
                month = Month.FEBRUARY;
                break;
            case "03":
                month = Month.MARCH;
                break;
            case "04":
                month = Month.APRIL;
                break;
            case "05":
                month = Month.MAY;
                break;
            case "06":
                month = Month.JUNE;
                break;
            case "07":
                month = Month.JULY;
                break;
            case "08":
                month = Month.AUGUST;
                break;
            case "09":
                month = Month.SEPTEMBER;
                break;
            case "10":
                month = Month.OCTOBER;
                break;
            case "11":
                month = Month.NOVEMBER;
                break;
            case "12":
                month = Month.DECEMBER;
                break;
        }
        return month;
    }

    /**
     * Extracts date from from a timestamp which is in following format
     * YYYY-MM-DD HH:mm
     * @param timestamp
     * @return
     */
    public static int getDateFromTimestamp(String timestamp) throws NumberFormatException
    {
        String [] arr = timestamp.split(" ");

        if(arr.length == 2)
        {
            String [] newArr = arr[0].split("-");
            if(newArr.length == 3)
            {
                return Integer.parseInt(newArr[2]);
            }
            throw numberFormatEx;
        }
        throw numberFormatEx;
    }

    /**
     * Extracts hour from from a timestamp which is in following format
     * YYYY-MM-DD HH:mm
     * @param timestamp
     * @return
     */
    public static int getHourFromTimestamp(String timestamp) throws NumberFormatException
    {
        String [] arr = timestamp.split(" ");

        if(arr.length == 2)
        {
            String [] newArr = arr[1].split(":");
            if(newArr.length == 2)
            {
                return Integer.parseInt(newArr[0]);
            }
            throw numberFormatEx;
        }
        throw numberFormatEx;
    }

    /**
     * Extracts minute from from a timestamp which is in following format
     * YYYY-MM-DD HH:mm
     * @param timestamp
     * @return
     */
    public static int getMinuteFromTimestamp(String timestamp) throws NumberFormatException
    {
        String [] arr = timestamp.split(" ");

        if(arr.length == 2)
        {
            String [] newArr = arr[1].split(":");
            if(newArr.length == 2)
            {
                return Integer.parseInt(newArr[1]);
            }
            throw numberFormatEx;
        }
        throw numberFormatEx;
    }

    public static LocalDateTime getLocalDateTime(String timestamp)
    {
        return LocalDateTime.of(DateTimeExtractor.getYearFromTimestamp(timestamp), DateTimeExtractor.getMonthFromTimestamp(timestamp), DateTimeExtractor.getDateFromTimestamp(timestamp), DateTimeExtractor.getHourFromTimestamp(timestamp), DateTimeExtractor.getMinuteFromTimestamp(timestamp));
    }
}
