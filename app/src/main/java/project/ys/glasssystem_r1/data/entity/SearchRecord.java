package project.ys.glasssystem_r1.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;


@Entity(tableName = "search_record_table")
public class SearchRecord extends BaseEntity {



    @ColumnInfo(name = "search_record")
    private String record;

    @ColumnInfo(name = "search_time")
    private long time;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "SearchRecord{" +
                "record='" + record + '\'' +
                ", time=" + time +
                '}';
    }
}
