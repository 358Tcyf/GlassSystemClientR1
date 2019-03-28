package project.ys.glasssystem_r1.ui.fragment.common;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.Date;

import cn.smssdk.SMSSDK;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.KeyboardChangeListener;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.mvp.contract.LoginContract;
import project.ys.glasssystem_r1.mvp.presenter.LoginPresenter;
import project.ys.glasssystem_r1.ui.activity.HomeActivity_;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.URL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.changeURL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showFailDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showSuccessDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showTipDialog;
import static project.ys.glasssystem_r1.util.utils.ToastUtils.showNormalToast;

@EFragment(R.layout.fragment_login_new)
public class LoginFragment extends SupportFragment implements LoginContract.View {
    public static LoginFragment newInstance() {
        return new LoginFragment_();
    }

    @ViewById(R.id.login_root)
    RelativeLayout root;
    @ViewById(R.id.tipLayout)
    RelativeLayout tipLayout;
    @ViewById(R.id.backImage)
    ImageView backImage;
    @ViewById(R.id.timeText)
    TextView timeText;
    @ViewById(R.id.inputAccount)
    EditText inputAccount;
    @ViewById(R.id.inputPasswordLayout)
    TextInputLayout inputPasswordLayout;
    @ViewById(R.id.inputPassword)
    EditText inputPassword;
    @ViewById(R.id.loginBtn)
    Button loginBtn;

    @ViewById(R.id.bottom_view)
    View bottom;

    @StringRes(R.string.logining)
    String logining;
    @StringRes(R.string.updating)
    String updating;
    @StringRes(R.string.noEmpty)
    String noEmpty;
    @StringRes(R.string.passwordEmpty)
    String passwordEmpty;
    @StringRes(R.string.cancel)
    String cancel;
    @StringRes(R.string.ok)
    String ok;
    @ColorRes(R.color.colorPrimaryText)
    int colorPrimaryText;
    @ColorRes(R.color.colorSecondaryText)
    int colorSecondaryText;
    @ColorRes(R.color.colorText_Icon)
    int colorText_Icon;

    private LoginPresenter loginPresenter;
    private String account;
    private String password;
    private QMUITipDialog loading;
    private UserBeanPlus currentUser;

    @AfterInject
    void afterInject() {
        loginPresenter = new LoginPresenter(this);
        SMSSDK.registerEventHandler(smsEventHandler);
    }

    @AfterViews
    void afterViews() {
        changeImage();
        new KeyboardChangeListener(_mActivity).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                RelativeLayout.LayoutParams Params =
                        (RelativeLayout.LayoutParams) bottom.getLayoutParams();
                if (isShow) {
                    tipLayout.setVisibility(View.GONE);
                } else {
                    tipLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        initEditTexView();

    }

    private void initEditTexView() {
        inputPasswordLayout.setPasswordVisibilityToggleDrawable(R.drawable.icon_pwd_selector);
        currentUser = CustomerApp.getInstance().getCurrentUser();
        if (currentUser!=null) {
            inputAccount.setText(currentUser.getNo());
            inputPassword.setText(currentUser.getPassword());
        }
    }

    @UiThread
    void changeImage() {
        Date time = new Date();
        int hour = time.getHours();
        switch (hour) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                timeText.setText("Morning");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 11:
            case 12:
            case 13:
                timeText.setText("Noon");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 14:
            case 15:
            case 16:
                timeText.setText("Afternoon");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 17:
            case 18:
                timeText.setText("Dawn");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                timeText.setText("Evening");
                backImage.setImageResource(R.mipmap.good_night_img);
                break;
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                timeText.setText("Night");
                backImage.setImageResource(R.mipmap.good_night_img);
                break;
        }
    }

    @Click(R.id.loginBtn)
    void loginBtn() {
        account = inputAccount.getText().toString();
        password = inputPassword.getText().toString();
        if (validInput()) {
            loading = showLoadingDialog(getContext(), logining);
            loading.show();
            loginPresenter.login(account, password);
        }
    }

    @Click(R.id.forgot_password)
    void resetPassword() {
        account = inputAccount.getText().toString();
        if (account.length() == 11) {
            new QMUIDialog.MessageDialogBuilder(getActivity())
                    .setTitle("这将会重置密码")
                    .setMessage("确定要重置吗？")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction(0, "重置", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            smsVerify(account);

                            dialog.dismiss();
                        }

                    })
                    .create().show();
        } else {
            showNormalToast(_mActivity, "请在账号一栏填写手机号");
        }

    }

    cn.smssdk.EventHandler smsEventHandler = new cn.smssdk.EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            new Handler().postDelayed(() -> {
                                showVerifyCodeDialog();
                            }, 1000);
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            loading = showLoadingDialog(_mActivity, updating);
                            loading.show();
                            loginPresenter.resetPassword(account);
                        } else {
                            // TODO 处理错误的结果
                            showFailDialog(_mActivity, "验证码不对");
                            new Handler().postDelayed(() -> {
                                showVerifyCodeDialog();
                            }, 1000);
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    private void showVerifyCodeDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("验证码")
                .setPlaceholder("请输入验证码:")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction(cancel, (dialog, index) -> dialog.dismiss())
                .addAction(ok, (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        smsSubmit(currentUser.getPhone(), text.toString());
                        dialog.dismiss();
                    } else {
                        showNormalToast(_mActivity, "请填入验证码");
                    }
                })
                .create().show();
    }

    private void smsVerify(String phone) {
        SMSSDK.getVerificationCode("86", phone);
    }

    private void smsSubmit(String phone, String code) {
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    @Click(R.id.changeUrl)
    void changeUrl() {
        showEditTextDialog();
    }

    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("修改网络通信地址")
                .setDefaultText(getURL())
//                .setPlaceholder(URL)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("默认", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        builder.getEditText().setText(URL);
                        changeURL(URL);
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        changeURL(text.toString());
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    @Override
    public boolean validInput() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        if (isEmpty(account)) {
            showTipDialog(getContext(), noEmpty);
            inputAccount.startAnimation(shake);
            return false;
        } else if (isEmpty(password)) {
            showTipDialog(getContext(), passwordEmpty);
            inputPassword.startAnimation(shake);
            return false;
        }
        return true;
    }

    @Override
    public void toHome() {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            toHomeActivity();
//            toHomeFragment();
        }, 2000);
    }


    public void toHomeActivity() {
        account = inputAccount.getText().toString();
        password = inputPassword.getText().toString();
        CustomerApp.getInstance().setCurrentUser(new UserBeanPlus(account, password));
        Intent intent = new Intent(getContext(), HomeActivity_.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void toHomeFragment() {
        CustomerApp.getInstance().setCurrentUser(new UserBeanPlus(inputAccount.getText().toString(), inputPassword.getText().toString()));
        _mActivity.setTheme(R.style.AppTheme);
        startWithPop(HomeFragment.newInstance());
    }


    @Override
    public void showOkMsg(String msg) {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showSuccessDialog(getContext(), msg);
        }, 2000);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showFailDialog(getContext(), errorMsg);
        }, 2000);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
