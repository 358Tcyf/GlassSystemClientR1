package project.ys.glasssystem_r1.util.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.io.File;

import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.common.HomeFragment_;

public class NotificationUtils {

    public static String strNotificationFolder = "/system/media/audio/notifications";

    /**
     * 显示一个普通的通知
     *
     * @param app 应用
     */
    public static void showNotification(CustomerApp app,String ticker, String title, String content) {
        Notification notification = new NotificationCompat.Builder(app)
                /*设置通知左边的大图标*/
                .setLargeIcon(BitmapFactory.decodeResource(app.getResources(), R.mipmap.ic_launcher))
                /*设置通知右边的小图标*/
                .setSmallIcon(R.mipmap.ic_launcher)
                /*通知首次出现在通知栏，带上升动画效果的*/
                .setTicker(ticker)
                /*设置通知的标题*/
                .setContentTitle(title)
                /*设置通知的内容*/
                .setContentText(content)
                /*通知产生的时间，会在通知信息里显示*/
                .setWhen(System.currentTimeMillis())
                /*设置该通知优先级**/
                .setPriority(Notification.PRIORITY_DEFAULT)
                //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //.setDefaults(Notification.DEFAULT_ALL)
                /*设置这个标志当用户单击面板就可以让通知将自动取消*/
                .setAutoCancel(true)
                /*设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)*/
                .setOngoing(false)
                .setContentIntent(PendingIntent.getActivity(app, 1, new Intent(app, HomeFragment_.class), PendingIntent.FLAG_CANCEL_CURRENT))
                .build();

        /*
         * 手机设置的默认提示音
         */
        if (app.getPushSet().isSound()) {
            notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        /*
         * vibrate属性是一个长整型的数组，用于设置手机静止和振动的时长，以毫秒为单位。
         * 参数中下标为0的值表示手机静止的时长，下标为1的值表示手机振动的时长， 下标为2的值又表示手机静止的时长，以此类推。
         */
        if (app.getPushSet().isVibrate()) {
            notification.vibrate = new long[]{0, 500};
        }

        /*
         * 手机处于锁屏状态时， LED灯就会不停地闪烁， 提醒用户去查看手机,下面是绿色的灯光一 闪一闪的效果
         */
        if (app.getPushSet().isFlags()) {
            notification.ledARGB = Color.GREEN;// 控制 LED 灯的颜色，一般有红绿蓝三种颜色可选
            notification.ledOnMS = 1000;// 指定 LED 灯亮起的时长，以毫秒为单位
            notification.ledOffMS = 1000;// 指定 LED 灯暗去的时长，也是以毫秒为单位
            notification.flags = Notification.FLAG_SHOW_LIGHTS;// 指定通知的一些行为，其中就包括显示
        }

        NotificationManager notificationManager = (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
        /*发起通知*/
        notificationManager.notify(0, notification);
    }


    /**
     * 悬挂式，支持6.0以上系统
     *
     * @param context
     */
    public static void showFullScreen(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(context, HomeFragment_.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(3, builder.build());
    }


    /**
     * 检测是否存在指定的文件夹,如果不存在则创建
     *
     * @param strFolder 文件夹路径
     */
    public static boolean hasFolder(String strFolder) {
        boolean btmp = false;
        File f = new File(strFolder);
        if (!f.exists()) {
            if (f.mkdirs()) {
                btmp = true;
            } else {
                btmp = false;
            }
        } else {
            btmp = true;
        }
        return btmp;
    }
}
