package project.ys.glasssystem_r1.presenter;

import java.util.Map;

import project.ys.glasssystem_r1.bean.UserBean;
import project.ys.glasssystem_r1.contract.MemberContract;
import project.ys.glasssystem_r1.contract.UserDetailContract;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.model.MemberModel;
import project.ys.glasssystem_r1.model.UserDetailModel;

public class UserDetailPresenter implements UserDetailContract.Presenter {

    private UserDetailContract.View userDetailView;
    private UserDetailModel memberModel;

    public UserDetailPresenter(UserDetailContract.View userDetailView) {
        this.userDetailView = userDetailView;
        memberModel = new UserDetailModel();
    }


    @Override
    public void getDetail(String no) {
        memberModel.getDetail(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                UserBean userBean = (UserBean) retResult.getData();
                userDetailView.setDetail(userBean);
            }

            @Override
            public void onFailed(String errorMsg) {

            }
        });
    }
}
