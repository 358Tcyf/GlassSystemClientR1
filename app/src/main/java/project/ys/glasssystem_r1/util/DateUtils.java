package project.ys.glasssystem_r1.util;

import android.content.Context;

import com.choota.dev.ctimeago.TimeAgo;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daylight
 * @date 2018/11/28 17:10
 */
public class DateUtils {
    public final static String format1 = "yyyy-MM-dd HH:mm:ss";
    public final static String format2 = "yyyy-MM-dd";
    public final static String MM = "MM";
    public final static String dd = "dd";


    public static String dateToStr(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date strToDate(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        ParsePosition pst = new ParsePosition(0);
        return sdf.parse(str, pst);
    }

    public static Date accurateToDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format1);
        String dateStr = sdf.format(date);
        sdf = new SimpleDateFormat(format2);
        ParsePosition pst = new ParsePosition(0);
        return sdf.parse(dateStr, pst);
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;

    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format1);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToStr(Context mContext, long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format1);
        TimeAgo timeAgo = new TimeAgo().locale(mContext).with(sdf);
        return timeAgo.getTimeAgo(date);

    }

    public static String dataToStr(Context mContext, Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        TimeAgo timeAgo = new TimeAgo().locale(mContext).with(sdf);
        return timeAgo.getTimeAgo(date);
    }

    public static String dataToStr(Context mContext, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format1);
        TimeAgo timeAgo = new TimeAgo().locale(mContext).with(sdf);
        return timeAgo.getTimeAgo(date);
    }

}
