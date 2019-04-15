package project.ys.glasssystem_r1.common.constant;

import com.tencent.mmkv.MMKV;

import static android.text.TextUtils.isEmpty;

public class HttpConstant {
    public static final String HTTP = "http://";
    private static final String BUGDOGGY = "47.102.41.26";
    public static final String URL = BUGDOGGY;
    public static final String PORT = ":9898";
    public static final String USER = "/user";
    public static final String LOGIN = "/login";
    public static final String USER_LIST = "/userList";
    public static final String SEARCH_USER = "/searchUserList";
    public static final String USER_INFO = "/userInfo";
    public static final String USER_DELETE = "/deleteUser";
    public static final String LATEST_NO = "/latestNo";
    public static final String ADD_USER = "/addUser";
    public static final String RESET_PASSWORD = "/resetPassword";
    public static final String UPDATE_USER = "/updateUser";
    public static final String UPDATE_PASSWORD = "/updatePassword";
    public static final String SET = "/set";
    public static final String GET_SETS = "/getSets";
    public static final String UPLOAD_SETS = "/uploadSet";
    public static final String GET_TAGS = "/getTags";
    public static final String UPDATE_TAGS = "/updateTags";
    public static final String GET_ALARM_TAGS = "/getAlarmTags";
    public static final String UPDATE_ALARM_TAGS = "/updateAlarmTags";


    public static final String PUSH = "/push";
    public static final String INSTANT = "/instantPush";
    public static final String UPDATE_PUSH = "/updatePushes";
    public static final String DOWNLOAD_PUSH = "/downloadPushes";
    public static final String UPDATE_ALARM = "/updateAlarms";
    public static final String DOWNLOAD_ALARM = "/downloadAlarms";

    public static final String FILE = "/file";
    public static final String UPLOAD = "/upload";


    public static void changeURL(String url) {
        MMKV http = MMKV.defaultMMKV();
        http.encode("url", url);
    }

    public static String getURL() {
        MMKV http = MMKV.defaultMMKV();
        String url = http.decodeString("url");
        if (isEmpty(url))
            return URL;
        else
            return url;

    }
}
