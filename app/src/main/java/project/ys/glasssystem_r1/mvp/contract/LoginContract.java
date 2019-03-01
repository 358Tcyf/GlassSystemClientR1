package project.ys.glasssystem_r1.mvp.contract;

import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface LoginContract {
    interface Model {
        void login(String account, String password, OnHttpCallBack<RetResult> callBack);
    }

    interface View {
        boolean validInput();

        void toHome();

        void showErrorMsg(String errorMsg);
    }

    interface Presenter {
        void login(String account, String password);
    }
}
