package project.ys.glasssystem_r1.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.dao.PushDao;
import project.ys.glasssystem_r1.data.entity.Push;

public class DatabaseHelper {
    private Context context;
    private MyDatabase giisDatabase;
    private PushDao pushDao;

    public DatabaseHelper(Context context) {
        this.context = context;
        giisDatabase = Room.databaseBuilder(context,
                MyDatabase.class, "my_database")
                .addCallback(new RoomDatabase.Callback() {
                    //第一次创建数据库时调用，但是在创建所有表之后调用的
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }

                    //当数据库被打开时调用
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .allowMainThreadQueries()//允许在主线程查询数据
                .addMigrations()//迁移数据库使用
                .fallbackToDestructiveMigration()//迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                .build();
        pushDao = giisDatabase.pushDao();
    }

    public void insertPush(Push push) {
        pushDao.insert(push);
    }

    public ArrayList<Push> getAllPush() {
        List<Push> list = pushDao.getAll();
        return (ArrayList<Push>) list;
    }

}
