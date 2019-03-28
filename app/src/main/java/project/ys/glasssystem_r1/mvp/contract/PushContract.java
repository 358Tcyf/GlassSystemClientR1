package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;

public interface PushContract {
    interface Model {

        void addPushItem(String content);

        List<Push> getPushList(String receiver);

        List<Push> getPushList(String receiver,int limit);

        List<Push> sortPushList(String receiver,int limit, String tag);

        void setRead(Push push);

        void deleteOne(int id);
    }

    interface View {
        void refreshView();

        void refreshFail();

        void showErrorMsg(String errorMsg);

        void setList(ArrayList list);
    }

    interface Presenter {
        void getList(String account,int limit);

        void sortList(String account,int limit, String tag);

        void setRead(Push push);

        void deleteOne(int id);

        int getTotal(String account);
    }
}
