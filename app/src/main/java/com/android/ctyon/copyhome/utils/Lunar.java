package com.android.ctyon.copyhome.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Lunar {
    static final String[] Big_Or_Small = new String[]{"大", "小", "大", "小", "大", "小", "大", "大", "小", "大", "小", "大"};
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(" yyyy年MM月dd日 ");
    static final String[] chineseNumber = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static final long[] lunarInfo = new long[]{19416, 19168, 42352, 21717, 53856, 55632, 91476, 22176, 39632, 21970, 19168, 42422, 42192, 53840, 119381, 46400, 54944, 44450, 38320, 84343, 18800, 42160, 46261, 27216, 27968, 109396, 11104, 38256, 21234, 18800, 25958, 54432, 59984, 28309, 23248, 11104, 100067, 37600, 116951, 51536, 54432, 120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423, 27808, 46416, 86869, 19872, 42448, 83315, 21200, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46496, 103846, 38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 21952, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 86390, 21168, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19415, 19152, 42192, 118966, 53840, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 111189, 27936, 44448};
    private String[] LunarHolDayName = new String[]{"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    private int day;
    private boolean leap;
    private int month;
    private int year;

    private static final int yearDays(int i) {
        int i2 = 348;
        for (int i3 = 32768; i3 > 8; i3 >>= 1) {
            if ((lunarInfo[i - 1900] & ((long) i3)) != 0) {
                i2++;
            }
        }
        return i2 + leapDays(i);
    }

    private static final int leapDays(int i) {
        if (leapMonth(i) == 0) {
            return 0;
        }
        if ((lunarInfo[i - 1900] & 65536) != 0) {
            return 30;
        }
        return 29;
    }

    private static final int leapMonth(int i) {
        return (int) (lunarInfo[i - 1900] & 15);
    }

    private static final int monthDays(int i, int i2) {
        if ((lunarInfo[i - 1900] & ((long) (65536 >> i2))) == 0) {
            return 29;
        }
        return 30;
    }

    public Lunar(Calendar calendar) {
        Date parse;
        try {
            parse = chineseDateFormat.parse(" 1900年1月31日 ");
        } catch (ParseException e) {
            e.printStackTrace();
            parse = null;
        }
        int time = (int) ((calendar.getTime().getTime() - parse.getTime()) / 86400000);
        int i = 1900;
        int i2 = 0;
        while (i < 2050 && time > 0) {
            i2 = yearDays(i);
            time -= i2;
            i++;
        }
        if (time < 0) {
            time += i2;
            i--;
        }
        this.year = i;
        int leapMonth = leapMonth(i);
        this.leap = false;
        int leap_int = 0;

        i = 1;
        i2 = time;
        time = 0;
        while (i < 13 && i2 > 0) {
            if (leapMonth <= 0 || i != leapMonth + 1 || (leap_int ^ 1) == 0) {
                time = monthDays(this.year, i);
            } else {
                i--;
                this.leap = true;
                time = leapDays(this.year);
            }
            i2 -= time;
            if (this.leap && i == leapMonth + 1) {
                this.leap = false;
            }
            boolean z = this.leap;
            i++;
        }
        if (i2 == 0 && leapMonth > 0 && i == leapMonth + 1) {
            if (this.leap) {
                this.leap = false;
            } else {
                this.leap = true;
                i--;
            }
        }
        if (i2 < 0) {
            time += i2;
            i--;
        } else {
            time = i2;
        }
        this.month = i;
        this.day = time + 1;
    }

    public static String getChinaDayString(int i) {
        String[] strArr = new String[]{"初", "十", "廿", "卅"};
        int i2 = i % 10 == 0 ? 9 : (i % 10) - 1;
        if (i > 30) {
            return "";
        }
        if (i == 10) {
            return "初十";
        }
        return strArr[i / 10] + chineseNumber[i2];
    }

    public String toString() {
        return (this.leap ? "闰" : "") + chineseNumber[this.month - 1] + "月" + getChinaDayString(this.day);
    }

    public String getChineseLunar() {
        return get_month() + "月" + get_date();
    }

    public String get_month() {
        return chineseNumber[this.month - 1];
    }

    public String get_date() {
        return getChinaDayString(this.day);
    }
}
