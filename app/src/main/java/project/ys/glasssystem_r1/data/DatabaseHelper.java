package project.ys.glasssystem_r1.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.BuildConfig;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.dao.PushDao;
import project.ys.glasssystem_r1.data.entity.Push;

import static project.ys.glasssystem_r1.util.utils.NotifyUtilsKt.notifyDefault;

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

    public PushDao getPushDao() {
        return pushDao;
    }

    public void insertPush(Push push) {
        pushDao.insert(push);
        Logger.d("新增了一条推送数据");
    }

    public ArrayList<Push> getAllPush() {
        List<Push> list = pushDao.getAll();
        return (ArrayList<Push>) list;
    }

    public ArrayList<Push> searchPush(String receiver, String order, String searchText) {
        List<Push> list = new ArrayList<>();
        Logger.d(order);
        Logger.d(searchText);
        if (order.equals(context.getString(R.string.sort_by_date))) {
            Logger.d(order);
            list = pushDao.findWithReceiverAndSearchText("%" + searchText + "%");
        }
        if (order.equals(context.getString(R.string.sort_by_read))) {
            Logger.d(order);
            list = pushDao.findWithReceiverAndSearchTextOrderByRead("%" + searchText + "%");
        }
        return (ArrayList<Push>) list;
    }

    public ArrayList<Push> sortAllPush(String receiver, String order) {
        List<Push> list = new ArrayList<>();
        if (order.equals(context.getString(R.string.sort_by_date)))
            list = pushDao.getAll();
        if (order.equals(context.getString(R.string.sort_by_read)))
            list = pushDao.getAllByRead();
        return (ArrayList<Push>) list;
    }


    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                if (!value.equals("not available"))
                    notifyDefault(context, "DebugDB", (String) value);
            } catch (Exception ignore) {

            }
        }
    }

    public void setPushRead(Push push) {
        push.setHaveRead(true);
        pushDao.update(push);
    }


    public void setDefault(String content, String submenu) {
        Logger.d(content + "///" + submenu);
        Push push = pushDao.findByContent(content);
        push.setDefaultSubMenu(submenu);
        Logger.d("///" + push);

        pushDao.update(push);
    }

    public void deletePush(int id) {
        pushDao.delete(pushDao.findById(id));
    }
}
