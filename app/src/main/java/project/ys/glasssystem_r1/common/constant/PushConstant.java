package project.ys.glasssystem_r1.common.constant;

public class PushConstant {
    public static String DailyProduce = "日生产量";
    public static String DailyTestRank = "日质检品级";
    public static String DailyConsume = "日生产能耗";

    public static String WeeklyProduce = "周生产量";
    public static String WeeklyTestRank = "周质检品级";
    public static String WeeklyConsume = "周生产能耗";

    public static String[] PUSH_TAGS = {DailyProduce, DailyTestRank, DailyConsume, WeeklyProduce, WeeklyTestRank, WeeklyConsume};
    public static  String[] PUSH_TIME = {"每日22:00", "次日08:00"};
}