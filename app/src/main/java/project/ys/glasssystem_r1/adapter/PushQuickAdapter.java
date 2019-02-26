package project.ys.glasssystem_r1.adapter;

import android.app.Activity;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.bean.PushBean;


public class PushQuickAdapter extends BaseQuickAdapter<PushBean, BaseViewHolder> {

    private Context mContext;

    public PushQuickAdapter(Activity mActivity, ArrayList<PushBean> data) {
        super(R.layout.item_push, data);
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, PushBean item) {

    }
}
