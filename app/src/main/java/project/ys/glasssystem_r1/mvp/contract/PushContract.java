package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;

public interface PushContract {
    interface Model {

        void addPushItem(String content);

        List<Push> getPushList(String receiver);
        List<Push> sortPushList(String receiver,String tag);
        void setRead(Push push);
    }

    interface View {
        void refreshView();

        void refreshFail();

        void setList(ArrayList list);
    }

    interface Presenter {
        void getList(String account);

        void sortList(String account,String tag);

        void setRead(Push push);
    }
}
