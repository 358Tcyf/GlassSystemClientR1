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
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.dao.AlarmDao;
import project.ys.glasssystem_r1.data.dao.BrowseCountDao;
import project.ys.glasssystem_r1.data.dao.PushDao;
import project.ys.glasssystem_r1.data.dao.SearchRecordDao;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.BrowseCount;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.data.entity.SearchRecord;

import static project.ys.glasssystem_r1.util.utils.NotificationUtils.showNotification;

public class DatabaseHelper {
    private Context context;
    private MyDatabase giisDatabase;
    private PushDao pushDao;
    private AlarmDao alarmDao;
    private SearchRecordDao searchDao;
    private BrowseCountDao browseCountDao;

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
        alarmDao = giisDatabase.alarmDao();
        searchDao = giisDatabase.searchDao();
        browseCountDao = giisDatabase.browseCountDao();
    }

    public PushDao getPushDao() {
        return pushDao;
    }

    public AlarmDao getAlarmDao() {
        return alarmDao;
    }

    public SearchRecordDao getSearchDao() {
        return searchDao;
    }

    public BrowseCountDao getBrowseCountDao() {
        return browseCountDao;
    }

    public void insertPush(Push push) {
        pushDao.insert(push);
        Logger.d("新增了一条数据推送");
    }

    public void insertAlarm(Alarm alarm) {
        alarmDao.insert(alarm);
        Logger.d("新增了一条预警推送");
    }

    public ArrayList<Push> getAllPush(String receiver) {
        List<Push> list = pushDao.getAll(receiver);
        return (ArrayList<Push>) list;
    }

    public ArrayList<Push> getAllPush(String receiver, int limit) {
        List<Push> list = pushDao.getAll(receiver, limit);
        return (ArrayList<Push>) list;
    }

    public ArrayList<Alarm> getAllAlarm(String receiver) {
        List<Alarm> list = alarmDao.getAll(receiver);
        return (ArrayList<Alarm>) list;
    }

    public ArrayList<Alarm> getAllAlarm(String receiver, int limit) {
        List<Alarm> list = alarmDao.getAll(receiver, limit);
        return (ArrayList<Alarm>) list;
    }

    public ArrayList<Push> searchPush(String receiver, String order, String searchText) {
        List<Push> list = new ArrayList<>();
        if (order.equals(context.getString(R.string.sort_by_date))) {
            list = pushDao.findWithReceiverAndSearchText(receiver, "%" + searchText + "%");
        }
        if (order.equals(context.getString(R.string.sort_by_read))) {
            Logger.d(order);
            list = pushDao.findWithReceiverAndSearchTextOrderByRead(receiver, "%" + searchText + "%");
        }
        return (ArrayList<Push>) list;
    }

    public ArrayList<Push> searchPush(String receiver, String order, String searchText, int limit) {
        List<Push> list = new ArrayList<>();
        if (order.equals(context.getString(R.string.sort_by_date))) {
            list = pushDao.findWithReceiverAndSearchText(receiver, "%" + searchText + "%", limit);
        }
        if (order.equals(context.getString(R.string.sort_by_read))) {
            Logger.d(order);
            list = pushDao.findWithReceiverAndSearchTextOrderByRead(receiver, "%" + searchText + "%", limit);
        }
        return (ArrayList<Push>) list;
    }

    public ArrayList<Alarm> searchAlarm(String receiver, String order, String searchText) {
        List<Alarm> list = new ArrayList<>();
        if (order.equals(context.getString(R.string.sort_by_date))) {
            list = alarmDao.findWithReceiverAndSearchText(receiver, "%" + searchText + "%");
        }
        if (order.equals(context.getString(R.string.sort_by_read))) {
            Logger.d(order);
            list = alarmDao.findWithReceiverAndSearchTextOrderByRead(receiver, "%" + searchText + "%");
        }
        return (ArrayList<Alarm>) list;
    }

    public ArrayList<Alarm> searchAlarm(String receiver, String order, String searchText, int limit) {
        List<Alarm> list = new ArrayList<>();
        if (order.equals(context.getString(R.string.sort_by_date))) {
            list = alarmDao.findWithReceiverAndSearchText(receiver, "%" + searchText + "%", limit);
        }
        if (order.equals(context.getString(R.string.sort_by_read))) {
            Logger.d(order);
            list = alarmDao.findWithReceiverAndSearchTextOrderByRead(receiver, "%" + searchText + "%", limit);
        }
        return (ArrayList<Alarm>) list;
    }

    public ArrayList<Push> sortAllPush(String receiver, int limit, String order) {
        List<Push> list = new ArrayList<>();
        if (order.equals(context.getString(R.string.sort_by_date)))
            list = pushDao.getAll(receiver, limit);
        if (order.equals(context.getString(R.string.sort_by_read)))
            list = pushDao.getAllByRead(receiver, limit);
        return (ArrayList<Push>) list;
    }

    public ArrayList<Alarm> sortAllAlarm(String receiver, int limit, String order) {
        List<Alarm> list = new ArrayList<>();
        if (order.equals(context.getString(R.string.sort_by_date)))
            list = alarmDao.getAll(receiver, limit);
        if (order.equals(context.getString(R.string.sort_by_read)))
            list = alarmDao.getAllByRead(receiver, limit);
        return (ArrayList<Alarm>) list;
    }

    public static void showDebugDBAddressLogToast(CustomerApp app) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                if (!value.equals("not available")) {
                    showNotification(app, "调试提醒", "查看本地数据库", (String) value);
//                    notifyDefault(app, "DebugDB", (String) value);

                }
            } catch (Exception ignore) {

            }
        }
    }

    public void setPushRead(Push push) {
        push.setHaveRead(true);
        pushDao.update(push);
    }

    public void setAlarmRead(Alarm alarm) {
        alarm.setHaveRead(true);
        alarmDao.update(alarm);
    }


    public void setDefault(Push push, String submenu) {
        push.setDefaultSubMenu(submenu);
        pushDao.update(push);
    }

    public void deletePush(int id) {
        pushDao.delete(pushDao.findById(id));
    }

    public void deleteAlarm(int id) {
        alarmDao.delete(alarmDao.findById(id));
    }

    public void insertSearch(String record) {
        long timeStamp = System.currentTimeMillis();
        SearchRecord searchRecord = searchDao.getOne(record);
        if (searchRecord == null) {
            searchRecord = new SearchRecord();
            searchRecord.setRecord(record);
            searchRecord.setTime(timeStamp);
            searchDao.insert(searchRecord);
        } else {
            searchRecord.setTime(timeStamp);
            searchDao.update(searchRecord);
        }
    }

    public List<SearchRecord> findRecentFive() {
        List<SearchRecord> fiveRecord = searchDao.getFive();
        return fiveRecord;
    }

    public void deleteRecord(SearchRecord searchRecord) {
        searchDao.delete(searchRecord);
    }

    public void insertBrowseCount(String tag, String receiver) {
        if (!cutOne(receiver, tag)) {
            BrowseCount browseCount = new BrowseCount(tag, 5, receiver);
            browseCountDao.insert(browseCount);
        }

    }

    public void insertBrowseCount(String tag, int count, String receiver) {
        if (cutOne(receiver, tag)) {
            BrowseCount browseCount = browseCountDao.findByNoAndTag(receiver, tag);
            browseCount.setCount(5);
            browseCountDao.update(browseCount);
        } else {
            BrowseCount browseCount = new BrowseCount(tag, count, receiver);
            browseCountDao.insert(browseCount);
        }

    }

    public boolean cutOne(String receiver, String tag) {
        if (browseCountDao.findByNoAndTag(receiver, tag) == null)
            return false;
        else {
            BrowseCount browseCount = browseCountDao.findByNoAndTag(receiver, tag);
            browseCount.setCount(browseCount.getCount() - 1);
            browseCountDao.update(browseCount);
            return true;
        }
    }

    public boolean addOne(String receiver, String tag) {
        if (browseCountDao.findByNoAndTag(receiver, tag) == null)
            return false;
        else {
            BrowseCount browseCount = browseCountDao.findByNoAndTag(receiver, tag);
            browseCount.setCount(browseCount.getCount() + 1);
            browseCountDao.update(browseCount);
            return true;
        }
    }

    public int getCount(String receiver, String tag) {
        BrowseCount browseCount = browseCountDao.findByNoAndTag(receiver, tag);
        return browseCount.getCount();
    }
}
