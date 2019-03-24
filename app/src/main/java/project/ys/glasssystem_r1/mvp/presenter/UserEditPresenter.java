package project.ys.glasssystem_r1.mvp.presenter;

import android.graphics.Bitmap;

import java.io.File;

import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.contract.UserEditContract;
import project.ys.glasssystem_r1.mvp.model.UserEditModel;

import static project.ys.glasssystem_r1.util.utils.FileUtils.bitmapToFile;

public class UserEditPresenter implements UserEditContract.Presenter {

    private UserEditContract.View userEditView;
    private UserEditModel userEditModel;

    public UserEditPresenter(UserEditContract.View userEditView) {
        this.userEditView = userEditView;
        userEditModel = new UserEditModel();
    }

    @Override
    public void uploadPic(Bitmap bitmap, String account) {
        File pic = bitmapToFile(bitmap);
        userEditModel.uploadPic(pic, account, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                userEditView.saveSuccess();
            }

            @Override
            public void onFailed(String errorMsg) {
                userEditView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void updateUser(String account, String email, String phone) {
        userEditModel.updateUser(account, email, phone, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                userEditView.saveSuccess();
            }

            @Override
            public void onFailed(String errorMsg) {
                userEditView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void updatePassword(String account, String oldPassword, String newPassword) {
        userEditModel.updatePassword(account, oldPassword, newPassword, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                userEditView.saveSuccess();
            }

            @Override
            public void onFailed(String errorMsg) {
                userEditView.showErrorMsg(errorMsg);
            }
        });
    }
}
