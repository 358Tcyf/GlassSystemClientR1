package project.ys.glasssystem_r1.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

@Entity(tableName = "browse_count_table")
public class BrowseCount extends BaseEntity {


    @Ignore
    public BrowseCount() {

    }

    public BrowseCount(String tag, int count, String receiver) {
        this.tag = tag;
        this.count = count;
        this.receiver = receiver;
    }

    @ColumnInfo(name = "push_tag")
    private String tag;
    @ColumnInfo(name = "browse_count")
    private int count;
    @ColumnInfo(name = "receiver_uuid")
    private String receiver;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "BrowseCount{" +
                "tag='" + tag + '\'' +
                ", count=" + count +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
