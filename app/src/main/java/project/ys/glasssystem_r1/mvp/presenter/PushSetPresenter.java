package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.data.bean.AlarmTag;
import project.ys.glasssystem_r1.data.bean.PushSet;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.contract.PushSetContract;
import project.ys.glasssystem_r1.mvp.model.PushSetModel;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;
import static project.ys.glasssystem_r1.common.constant.PushConstant.PRODUCE_TAGS;
import static project.ys.glasssystem_r1.common.constant.PushConstant.PUSH_TAGS;
import static project.ys.glasssystem_r1.common.constant.PushConstant.SALES_TAGS;

public class PushSetPresenter implements PushSetContract.Presenter {
    private PushSetContract.View pushSetView;
    private PushSetModel pushSetModel;
    private Context mContext;

    public PushSetPresenter(PushSetContract.View pushSetView) {
        this.pushSetView = pushSetView;
        pushSetModel = new PushSetModel();
    }

    public PushSetPresenter(PushSetContract.View pushSetView, Context context) {
        this.pushSetView = pushSetView;
        this.mContext = context;
        pushSetModel = new PushSetModel(context);
    }

    private String[] items = {};

    @Override
    public void getTags(String no) {
        if (no.startsWith("P"))
            items = PRODUCE_TAGS;
        else if (no.startsWith("S"))
            items = SALES_TAGS;
        else
            items = PUSH_TAGS;
        pushSetModel.getTags(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                List<String> tags = (List<String>) retResult.getData();
                List<Integer> checks = new ArrayList<>();
                for (int i = 0; i < items.length; i++) {
                    if (tags.contains(items[i])) {
                        checks.add(i);
                    }
                }
                pushSetView.showTagsChoices(checks);
            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void updateTags(String no, List<String> tags) {
        pushSetModel.updateTags(no, tags, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {

            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getSets(String no) {
        pushSetModel.getSets(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                CustomerApp.getInstance().setPushSet((PushSet) retResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
        pushSetModel.getTags(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void updateSets(String no, PushSet set) {
        pushSetModel.updateSets(no, set, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                pushSetView.showSuccess();
            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getAlarmTags(String no) {
        pushSetModel.getAlarmTags(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                pushSetView.setAlarmTags(parseArray(toJSONString(retResult.getData()), AlarmTag.class));
            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void uploadAlarmTags(String no, List<AlarmTag> alarmTags) {
        pushSetModel.uploadAlarmTags(no, alarmTags, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {

            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }
}
