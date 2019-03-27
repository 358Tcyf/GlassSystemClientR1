package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.PushContract;

public class PushModel implements PushContract.Model {

    private Context mContext;
    private DatabaseHelper helper;

    public PushModel() {
    }

    public PushModel(Context mContext) {
        this.mContext = mContext;
        helper = new DatabaseHelper(mContext);
    }

    @Override
    public void addPushItem(String content) {
        Push push = new Push();
        push.setContent(content);
        helper.insertPush(push);
    }

    @Override
    public List<Push> getPushList(String receiver) {
        return helper.getAllPush(receiver);
    }

    @Override
    public List<Push> sortPushList(String receiver, String tag) {
        return helper.sortAllPush(receiver, tag);
    }

    @Override
    public void setRead(Push push) {
        helper.setPushRead(push);
    }

    @Override
    public void deleteOne(int id) {
        helper.deletePush(id);
    }
}
