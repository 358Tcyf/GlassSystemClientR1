package project.ys.glasssystem_r1.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "push_table")
public class Push extends BaseEntity implements Parcelable {


    @Ignore
    public Push() {

    }

    public Push(String content) {
        this.content = content;
    }

    @ColumnInfo(name = "push_title")
    private String title;

    @ColumnInfo(name = "push_content")
    private String content;

    @ColumnInfo(name = "receiver_uuid")
    private String receiver;

    @ColumnInfo(name = "push_uuid")
    private String pushUuid;

    @ColumnInfo(name = "receiver_default_see")
    private String defaultSubMenu;
    @ColumnInfo(name = "push_createTime")
    private long createTime;

    @ColumnInfo(name = "receiver_haveRead")
    private boolean haveRead;

    protected Push(Parcel in) {
        title = in.readString();
        content = in.readString();
        receiver = in.readString();
        pushUuid = in.readString();
        defaultSubMenu = in.readString();
        createTime = in.readLong();
        haveRead = in.readByte() != 0;
    }

    public static final Creator<Push> CREATOR = new Creator<Push>() {
        @Override
        public Push createFromParcel(Parcel in) {
            return new Push(in);
        }

        @Override
        public Push[] newArray(int size) {
            return new Push[size];
        }
    };

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

    public String getDefaultSubMenu() {
        return defaultSubMenu;
    }

    public void setDefaultSubMenu(String defaultSubMenu) {
        this.defaultSubMenu = defaultSubMenu;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(receiver);
        dest.writeString(pushUuid);
        dest.writeString(defaultSubMenu);
        dest.writeLong(createTime);
        dest.writeByte((byte) (haveRead ? 1 : 0));
    }


    @Override
    public String toString() {
        return "Push{" +
                "title='" + title + '\'' +
                ",\n content='" + content + '\'' +
                ",\n receiver='" + receiver + '\'' +
                ",\n pushUuid='" + pushUuid + '\'' +
                ",\n defaultSubMenu='" + defaultSubMenu + '\'' +
                ",\n createTime=" + createTime +
                ",\n haveRead=" + haveRead +
                '}';
    }
}
