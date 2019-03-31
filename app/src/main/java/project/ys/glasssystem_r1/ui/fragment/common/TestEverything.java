package project.ys.glasssystem_r1.ui.fragment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;

@EFragment
public class TestEverything extends BaseBackFragment {
    public static TestEverything newInstance() {
        return new TestEverything_();
    }


    @ViewById(R.id.radioGroup)
    RadioGroup radioGroup;

    @AfterInject
    void afterInject() {

    }

    @AfterViews
    void afterView() {
        for (int i = 0; i < 3; i++)
            radioGroup.addView(initRadioBtn());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_test_everything, container, false);
        return attachToSwipeBack(view);
    }

    RadioButton initRadioBtn() {
        RadioButton button = new RadioButton(_mActivity);
        button.setChecked(false);
        button.setText("测试内容");
        button.setOnCheckedChangeListener(listener);
        return button;
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
                buttonView.setTextColor(_mActivity.getColor(R.color.pushUnread));
            else
                buttonView.setTextColor(_mActivity.getColor(R.color.pushRead));
        }
    };


}
