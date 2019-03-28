package project.ys.glasssystem_r1.ui.adapter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.entity.SearchRecord;

public class SearchRecordQuickAdapter extends BaseQuickAdapter<SearchRecord, BaseViewHolder>implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    public SearchRecordQuickAdapter(Activity mActivity, ArrayList<SearchRecord> data) {
        super(R.layout.item_search_record, data);
        this.mContext = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchRecord item) {
        helper.addOnClickListener(R.id.icon_clean);
        helper.setText(R.id.item_text, item.getRecord());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
