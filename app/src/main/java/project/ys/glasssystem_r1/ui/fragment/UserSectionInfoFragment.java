package project.ys.glasssystem_r1.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.mvp.presenter.UserDetailPresenter;

import static project.ys.glasssystem_r1.common.UserConstant.USER_ACCOUNT;


@EFragment(R.layout.fragment_section_detail)
public class UserSectionInfoFragment extends BaseBackFragment implements UserDetailContract.View {

    public static UserSectionInfoFragment newInstance(String no, String name) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        UserSectionInfoFragment fragment = new UserSectionInfoFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @ViewById(R.id.user_name)
    TextView userName;
    @ViewById(R.id.user_role)
    TextView userRole;
    @ViewById(R.id.user_no)
    TextView userNo;
    @ViewById(R.id.user_email)
    QMUILinkTextView userEmail;
    @ViewById(R.id.user_phone)
    QMUILinkTextView userPhone;


    private UserDetailPresenter userDetailPresenter;
    private String no;

    @AfterInject
    void afterInject() {
        userDetailPresenter = new UserDetailPresenter(this);
        no = getArguments().getString(USER_ACCOUNT);
    }


    @AfterViews
    void afterViews() {
        initCard();
    }

    private void initCard() {
        userDetailPresenter.getDetail(no);

    }

    @Override
    public void setDetail(UserBean user, String roleName) {
        resetCard(user, roleName);
    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @UiThread
    void resetCard(UserBean user, String roleName) {
        userName.setText(user.getName());
        userRole.setText(roleName);
        userNo.setText(user.getNo());
        userEmail.setText(user.getEmail());
        userPhone.setText(user.getPhone());
    }
}
