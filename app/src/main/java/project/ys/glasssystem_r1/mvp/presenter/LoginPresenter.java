package project.ys.glasssystem_r1.mvp.presenter;

import project.ys.glasssystem_r1.mvp.contract.LoginContract;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.model.LoginModel;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    private LoginModel loginModel;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        loginModel = new LoginModel();
    }

    @Override
    public void login(String account, String password) {
        loginModel.login(account, password, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                loginView.toHome();
            }

            @Override
            public void onFailed(String errorMsg) {
                loginView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void resetPassword(String account) {
        loginModel.resetPassword(account, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                loginView.showOkMsg((String) retResult.getMsg());
            }

            @Override
            public void onFailed(String errorMsg) {
                loginView.showErrorMsg(errorMsg);
            }
        });
    }
}
