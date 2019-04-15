package project.ys.glasssystem_r1.mvp.contract;

import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;

public interface ChartContract {
    interface Model {
        void cutFirst(String no, List<String> tags);

        void addOne(String no, String tag);

        void setRead(Push push);

        int getCount(String no, String tag);
    }

    interface View {
        void showTips(String tipMsg);
    }

    interface Presenter {
        void getTags(String no);

        void cutFirst(String no, List<String> tags);

        void addOne(String no, String tag);

        void setRead(Push push);

        void checkCount(String no, List<String> tags);
    }
}
