package project.ys.glasssystem_r1.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.ArrayList;
import java.util.Date;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.entity.Push;

import static project.ys.glasssystem_r1.util.DateUtils.MM;
import static project.ys.glasssystem_r1.util.DateUtils.dataToStr;
import static project.ys.glasssystem_r1.util.DateUtils.dd;
import static project.ys.glasssystem_r1.util.DateUtils.stampToDate;
import static project.ys.glasssystem_r1.util.DateUtils.stampToStr;


public class PushQuickAdapter extends BaseQuickAdapter<Push, BaseViewHolder> {

    private Context mContext;

    public PushQuickAdapter(Activity mActivity, ArrayList<Push> data) {
        super(R.layout.item_push_new, data);
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Push item) {
        helper.setText(R.id.pushTitle, item.getTitle());
        long longtime = item.getCreateTime();
        Date date = new Date(longtime);
        helper.setText(R.id.push_month, stampToDate(String.valueOf(item.getCreateTime()), MM));
        helper.setText(R.id.push_date, "/" + stampToDate(String.valueOf(item.getCreateTime()), dd));
        helper.setText(R.id.pushDate, stampToStr(mContext, item.getCreateTime()));
        QMUIRadiusImageView dateView = helper.getView(R.id.date_view);
        if (item.isHaveRead()) {
            helper.setText(R.id.pushRead, "Have Read");
            helper.setTextColor(R.id.pushRead, mContext.getColor(R.color.pushRead));
            helper.setImageResource(R.id.date_view, R.drawable.bg_date_view_read);
            helper.setTextColor(R.id.pushTitle, mContext.getColor(R.color.titleTextRead));
            helper.setTextColor(R.id.pushDate, mContext.getColor(R.color.timeTextRead));
            helper.setTextColor(R.id.push_month, mContext.getColor(R.color.dateTextRead));
            helper.setTextColor(R.id.push_date, mContext.getColor(R.color.dateTextRead));
        } else {
            helper.setText(R.id.pushRead, "Unread");
            helper.setTextColor(R.id.pushRead, mContext.getColor(R.color.pushUnread));
            helper.setImageResource(R.id.date_view, R.drawable.bg_date_view_unread);
            helper.setTextColor(R.id.pushTitle, mContext.getColor(R.color.titleTextUnread));
            helper.setTextColor(R.id.pushDate, mContext.getColor(R.color.timeTextUnread));
            helper.setTextColor(R.id.push_month, mContext.getColor(R.color.dateTextUnread));
            helper.setTextColor(R.id.push_date, mContext.getColor(R.color.dateTextUnread));
        }
    }
}
