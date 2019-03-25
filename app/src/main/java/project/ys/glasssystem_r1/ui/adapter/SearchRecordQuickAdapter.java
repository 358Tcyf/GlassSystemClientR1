package project.ys.glasssystem_r1.ui.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.entity.SearchRecord;

public class SearchRecordQuickAdapter extends BaseQuickAdapter<SearchRecord, BaseViewHolder> {

    public SearchRecordQuickAdapter(Activity mActivity, ArrayList<SearchRecord> data) {
        super(R.layout.item_search_record, data);
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchRecord item) {
        helper.setText(R.id.item_text, item.getRecord());
    }
}
