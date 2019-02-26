package project.ys.glasssystem_r1.support_fragment;

import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;

import static project.ys.glasssystem_r1.constant.UserConstant.USER_ACCOUNT;
import static project.ys.glasssystem_r1.constant.UserConstant.USER_NAME;


@EFragment(R.layout.fragment_section_detail)
public class UserSectionInfoFragment extends SupportFragment {

    public static UserSectionInfoFragment newInstance(String no, String name) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        args.putString(USER_NAME, name);
        UserSectionInfoFragment fragment = new UserSectionInfoFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @AfterViews
    void afterViews() {
        initCard();
    }

    private void initCard() {

    }

}
