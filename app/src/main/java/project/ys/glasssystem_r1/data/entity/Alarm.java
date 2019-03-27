package project.ys.glasssystem_r1.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;


@Entity(tableName = "alarm_table")
public class Alarm extends BaseEntity {

    @Ignore
    public Alarm() {

    }

    public Alarm(String content) {
        this.content = content;
    }

    @ColumnInfo(name = "alarm_title")
    private String title;

    @ColumnInfo(name = "alarm_content")
    private String content;

    @ColumnInfo(name = "receiver_uuid")
    private String receiver;

    @ColumnInfo(name = "alarm_uuid")
    private String alarmUuid;


    @ColumnInfo(name = "alarm_createTime")
    private long createTime;

    @ColumnInfo(name = "receiver_haveRead")
    private boolean haveRead;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAlarmUuid() {
        return alarmUuid;
    }

    public void setAlarmUuid(String alarmUuid) {
        this.alarmUuid = alarmUuid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isHaveRead() {
        return haveRead;
    }

    public void setHaveRead(boolean haveRead) {
        this.haveRead = haveRead;
    }


}
