package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.data.entity.SearchRecord;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface SearchContract {
    interface Model {
        List<Push> getPushList(String receiver, String search);

        List<Push> getPushList(String receiver, String order, String search);

        void setRead(Push push);

        void getUserList(String searchText, OnHttpCallBack<RetResult> onHttpCallBack);

        List<SearchRecord> getRecords();

        void insertRecord(String searchText);
    }

    interface View {
        void showNoData();

        void showErrorMsg(String errorMsg);

        void setList(ArrayList list);
        
        void showSearchRecord(ArrayList list);
    }

    interface Presenter {
        void searchPush(String account, String searchText);

        void sortList(String s, String tag, String searchText);

        void setRead(Push push);

        void searchUser(String searchText);

        void getRecord();

        void insertRecord(String searchText);
    }
}
