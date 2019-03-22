package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.SearchContract;

public class SearchModel implements SearchContract.Model {
    private Context mContext;
    private DatabaseHelper helper;

    public SearchModel() {
    }

    public SearchModel(Context mContext) {
        this.mContext = mContext;
        helper = new DatabaseHelper(mContext);
    }

    @Override
    public List<Push> getPushList(String receiver, String search) {
        return helper.searchPush(receiver, mContext.getString(R.string.sort_by_date), search);
    }

    @Override
    public List<Push> getPushList(String receiver, String order, String search) {
        return helper.searchPush(receiver, order, search);
    }

    @Override
    public void setRead(Push push) {
        helper.setPushRead(push);
    }
}
