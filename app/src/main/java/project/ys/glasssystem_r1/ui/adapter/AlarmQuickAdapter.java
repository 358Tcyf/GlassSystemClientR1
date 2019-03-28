package project.ys.glasssystem_r1.ui.adapter;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.AlarmLog;
import project.ys.glasssystem_r1.data.bean.AlarmSelectedBean;
import project.ys.glasssystem_r1.ui.widget.expandabletextview.ExpandableTextView;

import static project.ys.glasssystem_r1.data.bean.AlarmLog.ALARM_TAGS;
import static project.ys.glasssystem_r1.util.utils.DateUtils.stampToStr;


public class AlarmQuickAdapter extends BaseQuickAdapter<AlarmSelectedBean, BaseViewHolder> {

    private Context mContext;
    private boolean showSelected;

    public AlarmQuickAdapter(Activity mActivity, ArrayList<AlarmSelectedBean> data) {
        super(R.layout.item_alarm, data);
        this.showSelected = false;
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, AlarmSelectedBean item) {

        helper.setText(R.id.title, item.getAlarm().getTitle());
        helper.setText(R.id.createTime, stampToStr(mContext, item.getAlarm().getCreateTime()));
        helper.setEnabled(R.id.selected, false);
        helper.setChecked(R.id.selected, item.isSelected());
        helper.setGone(R.id.selected, showSelected);
        List<AlarmLog> alarmLogs = JSON.parseArray(item.getAlarm().getContent(), AlarmLog.class);
//        TextView content = helper.getView(R.id.content);
        ExpandableTextView content = helper.getView(R.id.content);

        String str = "预警日志\n";
        for (String tag : ALARM_TAGS) {
            for (AlarmLog log : alarmLogs) {
                if (log.getTag().equals(tag)) {
                    str += log.getLog();
                }

            }
        }
        content.setText(str);
        if (item.getAlarm().isHaveRead()){
            helper.setBackgroundColor(R.id.read, mContext.getColor(R.color.pushRead));
            content.setSateTextColor(mContext.getColor(R.color.pushRead));
        }
        else{
            helper.setBackgroundColor(R.id.read, mContext.getColor(R.color.pushUnread));
            content.setSateTextColor(mContext.getColor(R.color.pushUnread));
        }

    }

    public void toggleSelected() {
        showSelected = !showSelected;
    }

}
