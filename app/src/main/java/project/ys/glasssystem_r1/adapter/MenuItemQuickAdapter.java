package project.ys.glasssystem_r1.adapter;

import android.app.Activity;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.bean.MenuItemBean;

public class MenuItemQuickAdapter extends BaseQuickAdapter<MenuItemBean, BaseViewHolder> {

    private Context mContext;

    public MenuItemQuickAdapter(Activity mActivity, ArrayList<MenuItemBean> data) {
        super(R.layout.item_detail_menu, data);
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuItemBean item) {
        helper.setText(R.id.item_text, item.getText());
        helper.setText(R.id.item_detail_text, item.getDetailText());
        if (item.getRightIcon() != null)
            helper.setImageDrawable(R.id.item_right_icon, item.getRightIcon());

    }
}