package project.ys.glasssystem_r1.mvp.contract;

import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;

public interface PushContract {
    interface Model {

        void addPushItem(String content);

        List<Push> getPushList(String receiver);
    }

    interface View {
    }

    interface Presenter {
    }
}
