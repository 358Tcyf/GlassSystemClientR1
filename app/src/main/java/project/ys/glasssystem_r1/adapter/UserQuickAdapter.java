package project.ys.glasssystem_r1.adapter;

import android.app.Activity;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.bean.UserBean;

public class UserQuickAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {
    private Context mContext;

    public UserQuickAdapter(Activity mActivity, List<UserBean> data) {
        super(R.layout.item_member, data);
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {

        helper.setText(R.id.userName, item.getName());

    }
}
