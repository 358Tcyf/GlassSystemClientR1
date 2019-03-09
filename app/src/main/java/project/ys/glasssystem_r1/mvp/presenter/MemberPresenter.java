package project.ys.glasssystem_r1.mvp.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.data.bean.UserWithRoleBean;
import project.ys.glasssystem_r1.mvp.contract.MemberContract;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.model.MemberModel;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;

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
                List<Map<String, Object>> userMapList = (List<Map<String, Object>>) userMap.get("staffs");
                ArrayList<UserWithRoleBean> userList = new ArrayList<>();
                for (Map<String, Object> user : userMapList) {
                    UserWithRoleBean userBean = parseObject(toJSONString(user), UserWithRoleBean.class);
                    userList.add(userBean);
                }
                memberView.setList(userList);
            }

            @Override
            public void onFailed(String errorMsg) {
                memberView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void logOff(String no) {
        memberModel.logOff(no, new OnHttpCallBack<RetResult>() {
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
