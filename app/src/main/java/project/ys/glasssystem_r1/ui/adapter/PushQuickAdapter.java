package project.ys.glasssystem_r1.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.entity.Push;

import static project.ys.glasssystem_r1.util.DateUtils.dataToStr;
import static project.ys.glasssystem_r1.util.DateUtils.stampToDate;
import static project.ys.glasssystem_r1.util.DateUtils.stampToStr;


public class PushQuickAdapter extends BaseQuickAdapter<Push, BaseViewHolder> {

    private Context mContext;

    public PushQuickAdapter(Activity mActivity, ArrayList<Push> data) {
        super(R.layout.item_push, data);
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Push item) {
        helper.setText(R.id.pushTitle, item.getTitle());
        TextView haveRead = helper.getView(R.id.pushRead);
        if (item.isHaveRead()) {
            haveRead.setText("Have Read");
            haveRead.setTextColor(mContext.getColor(R.color.pushUnread));
        } else {
            haveRead.setText("Unread");
            haveRead.setTextColor(mContext.getColor(R.color.pushRead));
        }
        helper.setText(R.id.pushDate, stampToStr(mContext, item.getCreateTime()));
    }
}
