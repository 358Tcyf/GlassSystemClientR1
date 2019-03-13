package project.ys.glasssystem_r1.mvp.presenter;

import java.util.Map;

import project.ys.glasssystem_r1.data.bean.UserWithRoleBean;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.model.UserDetailModel;

import static com.alibaba.fastjson.JSON.parse;
import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;

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
                Map<String, Object> userMap = (Map<String, Object>) retResult.getData();
                UserWithRoleBean userBean = parseObject(toJSONString(userMap), UserWithRoleBean.class);
                userDetailView.setDetail(userBean);
            }

            @Override
            public void onFailed(String errorMsg) {
                userDetailView.showErrorView(errorMsg);
            }
        });
    }
}
