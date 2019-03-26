package project.ys.glasssystem_r1.ui.fragment.common;

import android.animation.Animator;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.ui.activity.HomeActivity_;
import project.ys.glasssystem_r1.ui.activity.LoginActivity_;

@EFragment(R.layout.fragment_splash)
public class SplashFragment extends SupportFragment {
    public static SplashFragment newInstance() {
        return new SplashFragment_();
    }

    @ViewById(R.id.lottieView)
    LottieAnimationView lottieView;
    @ViewById(R.id.loginBtn)
    TextView loginBtn;

    @AfterViews
    void afterViews() {
        int mAnimationTime = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        lottieView.animate().alpha(1f).setDuration(mAnimationTime)
                .setListener(null);
        lottieView.playAnimation();
        lottieView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                loginBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                UserBeanPlus user = CustomerApp.getInstance().getCurrentUser();
                if (user == null)
                    toLogin();
                else
                    toHome();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    @Click(R.id.loginBtn)
    public void loginBtn() {
        UserBeanPlus user = CustomerApp.getInstance().getCurrentUser();
        if (user == null)
            toLogin();
        else
            toHome();
    }


    public void toLogin() {
        Intent intent = new Intent(getContext(), LoginActivity_.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void toLoginFragment() {
        getActivity().setTheme(R.style.LoginTheme);
        startWithPop(LoginFragment.newInstance());
    }

    public void toHome() {
        Intent intent = new Intent(getContext(), HomeActivity_.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void toHomeFragment() {
        getActivity().setTheme(R.style.AppTheme);
        startWithPop(HomeFragment.newInstance());
    }
}
