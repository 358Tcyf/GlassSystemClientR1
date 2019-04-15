package project.ys.glasssystem_r1.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import project.ys.glasssystem_r1.data.dao.AlarmDao;
import project.ys.glasssystem_r1.data.dao.BrowseCountDao;
import project.ys.glasssystem_r1.data.dao.PushDao;
import project.ys.glasssystem_r1.data.dao.SearchRecordDao;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.BrowseCount;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.data.entity.SearchRecord;

@Database(entities = {Push.class, Alarm.class, SearchRecord.class, BrowseCount.class}, version = 4, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract PushDao pushDao();

    public abstract AlarmDao alarmDao();

    public abstract SearchRecordDao searchDao();

    public abstract BrowseCountDao browseCountDao();

}
