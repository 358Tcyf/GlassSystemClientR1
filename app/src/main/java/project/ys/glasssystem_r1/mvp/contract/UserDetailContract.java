package project.ys.glasssystem_r1.mvp.contract;

import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface UserDetailContract {
    interface Model {
        void getDetail(String name, OnHttpCallBack<RetResult> callBack);
    }

    interface View {
        void setDetail(UserBean user, String roleName);

        void showErrorMsg(String errorMsg);

    }

    interface Presenter {
        void getDetail(String name);
    }
}
