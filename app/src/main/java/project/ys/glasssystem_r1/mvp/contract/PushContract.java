package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;

public interface PushContract {
    interface Model {

        void addPushItem(String content);

        List<Push> getPushList(String receiver);

        void setRead(Push push);
    }

    interface View {
        void refreshFail();

        void setList(ArrayList list);
    }

    interface Presenter {
        void getList(String account);

        void setRead(Push push);
    }
}
