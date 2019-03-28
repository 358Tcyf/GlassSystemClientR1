package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.data.entity.SearchRecord;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface SearchContract {
    interface Model {
        List<Push> getPushList(String receiver, String search);

        List<Push> getPushList(String receiver, String search, int limit);

        List<Push> getPushList(String receiver, String order, String search);

        List<Push> getPushList(String receiver, String order, String search, int limit);

        List<Alarm> getAlarmList(String receiver, String search);

        List<Alarm> getAlarmList(String receiver, String search, int limit);

        List<Alarm> getAlarmList(String receiver, String order, String search);

        List<Alarm> getAlarmList(String receiver, String order, String search, int limit);

        void setRead(Push push);

        void getUserList(String searchText, OnHttpCallBack<RetResult> onHttpCallBack);

        List<SearchRecord> getRecords();

        void insertRecord(String searchText);

        void deleteRecord(SearchRecord searchRecord);
    }

    interface View {
        void showNoData();

        void showErrorMsg(String errorMsg);

        void setList(ArrayList list);

        void showSearchRecord(ArrayList list);
    }

    interface Presenter {
        void searchPush(String account, String searchText, int limit);

        void sortPushList(String s, String tag, String searchText, int limit);

        void searchAlarm(String account, String searchText, int limit);

        void sortAlarmList(String account, String order, String searchText, int limit);

        void setRead(Push push);

        void searchUser(String searchText);

        void getRecord();

        void insertRecord(String searchText);

        int getTotal(String account, String searchText, int searchClass);

        void deleteRecord(SearchRecord searchRecord);
    }
}
