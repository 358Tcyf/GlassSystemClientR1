package project.ys.glasssystem_r1.ui.fragment.second.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import moe.feng.common.stepperview.VerticalStepperItemView;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.mvp.contract.AddUserContract;
import project.ys.glasssystem_r1.mvp.presenter.AddUserPresenter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;

import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showFailDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showSuccessDialog;

@EFragment
public class AddUserFragment extends BaseBackFragment implements AddUserContract.View {


    public static AddUserFragment newInstance() {

        return new AddUserFragment_();
    }


    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;

    @ViewsById({R.id.stepper_0, R.id.stepper_1, R.id.stepper_2})
    ArrayList<VerticalStepperItemView> mSteppers;

    @ViewById(R.id.inputName)
    MaterialEditText inputName;

    @ViewById(R.id.roleSpinner)
    MaterialSpinner roleSpinner;

    @ViewById(R.id.newUserNo)
    MaterialEditText newUserNo;

    @ViewById(R.id.inputEmail)
    EditText inputEmail;

    @ViewById(R.id.inputPhone)
    EditText inputPhone;

    @StringArrayRes(R.array.roles)
    String[] roles;
    @StringRes(R.string.adding)
    String adding;
    @StringRes(R.string.addSuccess)
    String addSuccess;

    private VerticalStepperItemView steppers[] = new VerticalStepperItemView[3];
    private int roleId;
    private String name, no, email, phone;
    private AddUserPresenter addUserPresenter;
    private UserBean user;
    private QMUITipDialog loading;

    @AfterInject
    void afterInject() {
        addUserPresenter = new AddUserPresenter(this);
    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initSteppers();
        initSpinner();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        return attachToSwipeBack(view);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
    }

    private void initSteppers() {
        mSteppers.toArray(steppers);
        VerticalStepperItemView.bindSteppers(steppers);
        user = new UserBean();
    }

    private void initSpinner() {
        roleSpinner.setItems(roles);
        roleId = 1;
        addUserPresenter.getLatestNo(roleId);
        roleSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                roleId = view.getSelectedIndex() + 1;
                addUserPresenter.getLatestNo(roleId);
            }
        });
    }

    @Click({R.id.button_next_0})
    void step0Next() {
        name = inputName.getText().toString();
        steppers[0].nextStep();
        steppers[0].setSummaryFinished(name);
        user.setName(name);
    }

    @Click({R.id.button_skip_0})
    void step0Skip() {
        steppers[0].nextStep();
        steppers[0].setSummaryFinished("不设置");
    }

    @Click({R.id.button_next_1})
    void step1Next() {
        steppers[1].nextStep();
        user.setNo(no);
    }

    @Click({R.id.button_prev_1})
    void step0Prev() {
        steppers[1].prevStep();
    }

    @Click({R.id.button_next_2})
    void step2Next() {
        steppers[2].nextStep();
        email = inputPhone.getText().toString();
        phone = inputPhone.getText().toString();
        user.setEmail(email);
        user.setPhone(phone);
        addUser();
    }

    private void addUser() {
        loading = showLoadingDialog(getContext(), adding);
        loading.show();
        addUserPresenter.addUser(user, roleId);
    }

    @Click({R.id.button_prev_2})
    void step2Prev() {
        steppers[2].prevStep();
    }

    @Override
    public void setNo(String no) {
        this.no = no;
        setNoText();
    }

    @Override
    public void addSuccess() {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showSuccessDialog(getContext(), addSuccess);
        }, 500);
        EventBusActivityScope.getDefault(_mActivity).post(new RefreshListEvent());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showFailDialog(getContext(), errorMsg);
        }, 500);
    }

    @UiThread
    void setNoText() {
        newUserNo.setText(no);
        Logger.d(no);
    }
}
