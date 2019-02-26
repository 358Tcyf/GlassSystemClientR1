package project.ys.glasssystem_r1.presenter;

import android.util.Log;

import java.util.List;
import java.util.Map;

import project.ys.glasssystem_r1.bean.UserBean;
import project.ys.glasssystem_r1.contract.MemberContract;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.model.MemberModel;

public class MemberPresenter implements MemberContract.Presenter {
    private MemberContract.View memberView;
    private MemberModel memberModel;

    public MemberPresenter(MemberContract.View memberView) {
        this.memberView = memberView;
        memberModel = new MemberModel();
    }

    @Override
    public void userList() {
        memberModel.userList(new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                Map<String, Object> userMap = (Map<String, Object>) retResult.getData();

                List<UserBean> useList = (List<UserBean>) retResult.getData();
                Log.d("Member", "MemberPresenter: "+retResult.getData().toString());
                memberView.setMap(userMap);
            }

            @Override
            public void onFailed(String errorMsg) {
                memberView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void logOff(String no) {
        memberModel.logOff(no,new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                memberView.refreshView();
            }

            @Override
            public void onFailed(String errorMsg) {
                memberView.showErrorMsg(errorMsg);
            }
        });
    }
}
