package project.ys.glasssystem_r1.ui.adapter;

import android.app.Activity;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.ui.widget.expandabletextview.ExpandableTextView;

import static project.ys.glasssystem_r1.util.utils.DateUtils.stampToStr;


public class AlarmQuickAdapter extends BaseQuickAdapter<Alarm, BaseViewHolder> {

    private Context mContext;
    private boolean showSelected;

    public AlarmQuickAdapter(Activity mActivity, ArrayList<Alarm> data) {
        super(R.layout.item_alarm, data);
        this.showSelected = false;
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Alarm item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.content, item.getContent());
        helper.setText(R.id.createTime, stampToStr(mContext, item.getCreateTime()));
//        ExpandableTextView content = helper.getView(R.id.content);
//        content.setText(item.getContent());

    }

    public void toggleSelected() {
        showSelected = !showSelected;
    }

}
