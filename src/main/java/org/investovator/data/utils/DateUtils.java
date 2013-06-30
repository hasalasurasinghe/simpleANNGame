package org.investovator.data.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author rajith
 * @version $Revision$
 */
public class DateUtils {

    /**
     * Evaluate to true if reference date is before the CSV date
     * column's date (ex: refDate = 1/27/2012 , csvDate 1/31/2012)
     *
     * @param dateFormat date format of the csv file
     * @param refDate    reference date
     * @param csvDate    csv column's date
     * @return true if the reference date is before the CSV date
     */
    public static boolean isDateBefore(String dateFormat, String refDate, String csvDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date1 = sdf.parse(refDate);
            Date date2 = sdf.parse(csvDate);
            return date1.compareTo(date2) <= 0;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Evaluate to true if reference date is after the CSV date
     * column's date (ex: refDate = 1/31/2012 , csvDate 1/27/2012)
     *
     * @param dateFormat date format of the csv file
     * @param refDate    reference date
     * @param csvDate    csv column's date
     * @return true if the reference date is after the CSV date
     */
    public static boolean isDateAfter(String dateFormat, String refDate, String csvDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date1 = sdf.parse(refDate);
            Date date2 = sdf.parse(csvDate);
            return date1.compareTo(date2) >= 0;
        } catch (ParseException e) {
            return false;
        }
    }
}
