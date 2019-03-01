package project.ys.glasssystem_r1.mvp.contract;

import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface AddUserContract {
    interface Model {
        void getLatestNo(int roleId, OnHttpCallBack<RetResult> onHttpCallBack);

        void addUser(UserBean user, int roleId, OnHttpCallBack<RetResult> onHttpCallBack);
    }

    interface View {
        void setNo(String no);

        void addSuccess();

        void showErrorMsg(String errorMsg);
    }

    interface Presenter {

        void getLatestNo(int roleId);

        void addUser(UserBean user, int roleId);
    }
}
