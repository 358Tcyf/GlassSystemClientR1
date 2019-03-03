package project.ys.glasssystem_r1.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import project.ys.glasssystem_r1.data.dao.PushDao;
import project.ys.glasssystem_r1.data.entity.Push;

@Database(entities = {Push.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract PushDao pushDao();

}
