package project.ys.glasssystem_r1.mvp.contract;

import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface ChartContract {
    interface Model {
        void cutFirst(String no, List<String> tags);

        void addOne(String no, String tag);

        void setRead(Push push);

        int getCount(String no, String tag);

        void cancelSmartSub(String no, OnHttpCallBack<RetResult> callBack);

        void cancelTags(String no, List<String> tags, OnHttpCallBack<RetResult> callBack);
    }

    interface View {
        void showTips( List<String> tips);
    }

    interface Presenter {
        void getTags(String no);

        void cutFirst(String no, List<String> tags);

        void addOne(String no, String tag);

        void setRead(Push push);

        void checkCount(String no, List<String> tags);

        void cancelSmartSub(String no);

        void cancelTags(String no, List<String> tags);
    }
}
