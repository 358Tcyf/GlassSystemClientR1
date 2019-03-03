package project.ys.glasssystem_r1.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;


@Entity(tableName = "push_table")
public class Push extends BaseEntity {

    @ColumnInfo(name = "push_title")
    private String title;

    @ColumnInfo(name = "push_content")
    private String content;

    @ColumnInfo(name = "receiver_uuid")
    private String receiver;

    @ColumnInfo(name = "push_uuid")
    private String pushUuid;

    @ColumnInfo(name = "push_createTime")
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

    public String getPushUuid() {
        return pushUuid;
    }

    public void setPushUuid(String pushUuid) {
        this.pushUuid = pushUuid;
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
