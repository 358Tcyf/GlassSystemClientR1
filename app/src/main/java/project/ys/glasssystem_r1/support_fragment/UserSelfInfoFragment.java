package project.ys.glasssystem_r1.support_fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;

@EFragment(R.layout.fragment_self_detail)
public class UserSelfInfoFragment extends SupportFragment {
    public static UserSelfInfoFragment newInstance() {
        return new UserSelfInfoFragment_();
    }

    @ViewById(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    @AfterViews
    void afterViews() {
        initGroupListView();
    }

    private void initGroupListView() {
        QMUICommonListItemView nameItem = mGroupListView.createItemView("姓名");
        nameItem.setOrientation(QMUICommonListItemView.VERTICAL);
        nameItem.setDetailText("XX");

        QMUICommonListItemView emailItem = mGroupListView.createItemView("邮箱");
        emailItem.setOrientation(QMUICommonListItemView.VERTICAL);
        emailItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        emailItem.setDetailText("XX");

        QMUICommonListItemView phoneItem = mGroupListView.createItemView("手机");
        phoneItem.setOrientation(QMUICommonListItemView.VERTICAL);
        phoneItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        phoneItem.setDetailText("XX");
        QMUIGroupListView.newSection(getContext())
                .addItemView(nameItem, onClickListener)
                .addItemView(emailItem, onClickListener)
                .addItemView(phoneItem, onClickListener)
                .addTo(mGroupListView);
    }
    private View.OnClickListener onClickListener = this::onClick;

    private void onClick(View v) {
        if (v instanceof QMUICommonListItemView) {
            CharSequence text = ((QMUICommonListItemView) v).getText();

        }
    }
}
