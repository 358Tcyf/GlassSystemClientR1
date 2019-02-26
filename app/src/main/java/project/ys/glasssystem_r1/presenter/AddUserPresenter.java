package project.ys.glasssystem_r1.presenter;

import com.orhanobut.logger.Logger;

import project.ys.glasssystem_r1.bean.UserBean;
import project.ys.glasssystem_r1.contract.AddUserContract;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.model.AddUserModel;

public class AddUserPresenter implements AddUserContract.Presenter {
    private AddUserContract.View addUserView;
    private AddUserModel addUserModel;

    public AddUserPresenter(AddUserContract.View addUserView) {
        this.addUserView = addUserView;
        addUserModel = new AddUserModel();
    }

    @Override
    public void getLatestNo(int roleId) {
        addUserModel.getLatestNo(roleId, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                addUserView.setNo((String) retResult.getMsg());

            }

            @Override
            public void onFailed(String errorMsg) {
            }
        });
    }

    @Override
    public void addUser(UserBean user, int roleId) {
        addUserModel.addUser(user, roleId, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                addUserView.addSuccess();

            }

            @Override
            public void onFailed(String errorMsg) {
                addUserView.showErrorMsg(errorMsg);
            }
        });
    }
}
