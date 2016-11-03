package common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils
{

    public final static Long MINUTE_IN_MS = 60 * 1000L;
    public final static Long HOUR_IN_MS = 60 * 60 * 1000L;
    public final static Long DAY_IN_MS = 24 * 60 * 60 * 1000L;

    public final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * Convert yyyy-MM-dd HH:mm:ss.SSS Date from String to Timestamp
     */
    public static Timestamp convertToTimestamp(String stringToFormat)
    {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	try
	{
	    Date date = dateFormat.parse(stringToFormat);
	    Timestamp tstamp = new Timestamp(date.getTime());
	    return tstamp;
	} catch (ParseException e)
	{
	    return null;
	}
    }

    public static Timestamp convertToTimestamp(Date date, TimeZone timeZone)
    {
	DateFormat df = DateFormat.getDateTimeInstance();
	df.setTimeZone(timeZone);
	return convertToTimestamp(df.format(date.getTime()));
    }

    public static String toString(Date date)
    {
	if (date == null)
	{
	    return null;
	}
	return SDF.format(date);
    }

    /**
     * parse a date from String, format is yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * 
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate)
    {
	if (strDate == null)
	{
	    return null;
	}
	try
	{
	    return SDF.parse(strDate.replaceAll(":(?=..$)", ""));
	} catch (ParseException e)
	{
	    e.printStackTrace();
	}
	return null;
    }

    public static Date getDatePlusSecondsFromNow(long seconds)
    {
	Calendar cal = new GregorianCalendar();
	cal.set(Calendar.SECOND, (int) (new Date().getSeconds() + seconds));
	return cal.getTime();
    }

    public static Date getDatePlusMinutesFromNow(int minutes)
    {
	Calendar cal = new GregorianCalendar();
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MINUTE, new Date().getMinutes() + minutes);
	return cal.getTime();
    }

    /**
     * Returns an instance of Date set to tomorow at midnight
     * 
     * @return
     */
    public static Date getTomorrowMidnight()
    {
	Calendar cal = new GregorianCalendar();
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.add(Calendar.DAY_OF_YEAR, 1);
	return cal.getTime();
    }

    /**
     * Returns an instance of Date set to first day of next month at midnight
     * 
     * @return
     */
    public static Date getFirstDayOfMonthMidnight()
    {
	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.add(Calendar.MONTH, 1);
	calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
	Date nextMonthFirstDay = calendar.getTime();
	return nextMonthFirstDay;
    }

    /**
     * Get current date for file name.
     * 
     * We save date in format: "dd.MM.yy"
     * 
     * @return current date.
     */
    public static String currentDate(String format)
    {
	DateFormat dateFormat = new SimpleDateFormat(format);
	Date date = new Date();
	return dateFormat.format(date);
    }
}