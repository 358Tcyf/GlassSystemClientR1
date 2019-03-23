package project.ys.glasssystem_r1.mvp.contract;

import android.graphics.Bitmap;

import java.io.File;

import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface UserEditContract {
    interface Model {
        void uploadPic(File file, String account, OnHttpCallBack<RetResult> callBack);

        void updateUser(String account, String email, String phone, OnHttpCallBack<RetResult> callBack);
    }

    interface View {
        void saveSuccess();

        void showErrorMsg(String errorMsg);
    }

    interface Presenter {
        void uploadPic(Bitmap bitmap, String account);

        void updateUser(String account, String email, String phone);
    }
}
