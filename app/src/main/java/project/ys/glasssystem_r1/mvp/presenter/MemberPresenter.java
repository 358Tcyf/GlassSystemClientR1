package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.contract.MemberContract;
import project.ys.glasssystem_r1.mvp.model.MemberModel;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;

public class MemberPresenter implements MemberContract.Presenter {
    private MemberContract.View memberView;
    private MemberModel memberModel;
    private Context mContext;

    public MemberPresenter(MemberContract.View memberView) {
        this.memberView = memberView;
        memberModel = new MemberModel();
    }

    public MemberPresenter(MemberContract.View memberView, Context mContext) {
        this.memberView = memberView;
        this.mContext = mContext;
        memberModel = new MemberModel();
    }

    @Override
    public void userList() {
        memberModel.userList(new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                Map<String, Object> userMap = (Map<String, Object>) retResult.getData();
                List<Map<String, Object>> userMapList = (List<Map<String, Object>>) userMap.get("staffs");
                memberView.setList((ArrayList) parseArray(toJSONString(userMapList), UserBeanPlus.class));
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
