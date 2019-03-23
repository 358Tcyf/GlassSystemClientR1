package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface MemberContract {
    interface Model {
        void userList(OnHttpCallBack<RetResult> callBack);

        void logOff(String no, OnHttpCallBack<RetResult> onHttpCallBack);
    }

    interface View {
        void refreshView();

        void refreshFail();

        void showErrorMsg(String errorMsg);

        void setList(ArrayList list);

    }

    interface Presenter {
        void userList();

        void logOff(String no);
    }
}
