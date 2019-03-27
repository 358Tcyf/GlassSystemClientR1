package project.ys.glasssystem_r1.ui.adapter;

import android.app.Activity;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.PushSelectedBean;

import static project.ys.glasssystem_r1.util.utils.DateUtils.MM;
import static project.ys.glasssystem_r1.util.utils.DateUtils.dd;
import static project.ys.glasssystem_r1.util.utils.DateUtils.stampToDate;
import static project.ys.glasssystem_r1.util.utils.DateUtils.stampToStr;


public class PushQuickAdapter extends BaseQuickAdapter<PushSelectedBean, BaseViewHolder> {

    private Context mContext;
    private boolean showSelected;

    public PushQuickAdapter(Activity mActivity, ArrayList<PushSelectedBean> data) {
        super(R.layout.item_push, data);
        this.showSelected = false;
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, PushSelectedBean item) {
        helper.setText(R.id.pushTitle, item.getPush().getTitle());
        helper.setText(R.id.push_month, stampToDate(String.valueOf(item.getPush().getCreateTime()), MM));
        helper.setText(R.id.push_date, "/" + stampToDate(String.valueOf(item.getPush().getCreateTime()), dd));
        helper.setText(R.id.pushDate, stampToStr(mContext, item.getPush().getCreateTime()));
        if (item.getPush().isHaveRead()) {
            helper.setText(R.id.pushRead, "Have Read");
            helper.setTextColor(R.id.pushRead, mContext.getColor(R.color.pushRead));
            helper.setImageResource(R.id.date_dot, R.drawable.bg_date_view_read);
            helper.setTextColor(R.id.pushTitle, mContext.getColor(R.color.titleTextRead));
            helper.setTextColor(R.id.pushDate, mContext.getColor(R.color.timeTextRead));
            helper.setTextColor(R.id.push_month, mContext.getColor(R.color.dateTextRead));
            helper.setTextColor(R.id.push_date, mContext.getColor(R.color.dateTextRead));
        } else {
            helper.setText(R.id.pushRead, "Unread");
            helper.setTextColor(R.id.pushRead, mContext.getColor(R.color.pushUnread));
            helper.setImageResource(R.id.date_dot, R.drawable.bg_date_view_unread);
            helper.setTextColor(R.id.pushTitle, mContext.getColor(R.color.titleTextUnread));
            helper.setTextColor(R.id.pushDate, mContext.getColor(R.color.timeTextUnread));
            helper.setTextColor(R.id.push_month, mContext.getColor(R.color.dateTextUnread));
            helper.setTextColor(R.id.push_date, mContext.getColor(R.color.dateTextUnread));
        }
        helper.setEnabled(R.id.push_selected, false);
        helper.setChecked(R.id.push_selected, item.isSelected());
        if (showSelected) {
            helper.setGone(R.id.push_selected, true);
        } else {
            helper.setGone(R.id.push_selected, false);
        }
    }

    public void toggleSelected() {
        showSelected = !showSelected;
    }

}
