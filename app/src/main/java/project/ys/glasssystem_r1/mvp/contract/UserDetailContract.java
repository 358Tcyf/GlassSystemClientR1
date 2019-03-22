package project.ys.glasssystem_r1.mvp.contract;

import project.ys.glasssystem_r1.data.bean.UserBeanOrderByName;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface UserDetailContract {
    interface Model {
        void getDetail(String name, OnHttpCallBack<RetResult> callBack);
    }

    interface View {
        void setDetail(UserBeanOrderByName user );

        void showErrorView(String errorMsg);

    }

    interface Presenter {
        void getDetail(String name);
    }
}
