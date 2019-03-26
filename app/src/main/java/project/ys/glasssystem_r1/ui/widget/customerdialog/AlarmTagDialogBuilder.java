package project.ys.glasssystem_r1.ui.widget.customerdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.AlarmTag;

import static android.text.TextUtils.isEmpty;

public class AlarmTagDialogBuilder extends QMUIDialog.CustomDialogBuilder {

    @BindView(R.id.tagSpinner)
    MaterialSpinner tagSpinner;
    @BindView(R.id.min_value_input)
    EditText minValueInput;
    @BindView(R.id.max_value_input)
    EditText maxValueInput;

    @BindArray(R.array.alarmTags)
    String[] arrayTags;

    private AlarmTag alarmTag;

    private int selectIndex;

    private List<String> listTags = new ArrayList<>();
    private List<AlarmTag> alarmTags = new ArrayList<>();

    public AlarmTagDialogBuilder(Context context, List<AlarmTag> alarmTags) {
        super(context);
        this.alarmTags = alarmTags;
    }

    @Override
    protected void onCreateContent(QMUIDialog dialog, ViewGroup parent, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alarm_tag, parent);
        ButterKnife.bind(this, view);
        initSpinner();
    }


    private void initSpinner() {
        for (String tag : arrayTags) {
            listTags.add(tag);
        }
        int s = listTags.size();
        for (AlarmTag tag : alarmTags) {
            for (int i = 0; i < s; i++) {
                if (tag.getContent().equals(listTags.get(i))) {
                    listTags.remove(i);
                    s--;
                    i--;
                }
            }

        }

        tagSpinner.setItems(listTags);
        selectIndex = 0;
        tagSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            selectIndex = view.getSelectedIndex();
            if (alarmTag != null)
                alarmTag.setContent(getTag());
            else
                alarmTag = new AlarmTag(getTag());
        });
    }

    public String getMinValue() {
        return minValueInput.getText().toString();
    }

    public String getMaxValue() {
        return maxValueInput.getText().toString();
    }

    public String getTag() {
        return listTags.get(selectIndex);
    }

    @OnTextChanged(value = {R.id.min_value_input, R.id.max_value_input}, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChange() {
        if (alarmTag != null) {
            if (!isEmpty(getMaxValue()))
                alarmTag.setMax(Float.parseFloat(getMaxValue()));
            if (!isEmpty(getMinValue()))
                alarmTag.setMin(Float.parseFloat(getMinValue()));
        } else {
            alarmTag = new AlarmTag(getTag());
        }
    }

    public AlarmTag getAlarmTag() {
        onTextChange();
        if (isEmpty(getMaxValue()) || isEmpty(getMinValue()))
            return null;
        else
            return alarmTag;
    }
}
