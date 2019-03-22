package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;

public interface SearchContract {
    interface Model {
        List<Push> getPushList(String receiver, String search);

        List<Push> getPushList(String receiver, String order, String search);

        void setRead(Push push);
    }

    interface View {
        void showNoData();

        void showErrorMsg(String errorMsg);


        void setList(ArrayList list);
    }

    interface Presenter {
        void searchPush(String account, String searchText);

        void sortList(String s, String tag, String searchText);

        void setRead(Push push);
    }
}