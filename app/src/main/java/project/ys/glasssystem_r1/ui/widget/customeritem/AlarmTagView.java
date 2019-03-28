package project.ys.glasssystem_r1.ui.widget.customeritem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.ys.glasssystem_r1.R;

public class AlarmTagView {

    public static LinearLayout parent;

    public static TextView alarmValues;


    public static View createItem(Context context, String values) {
        LayoutInflater inflater = LayoutInflater.from(context);
        parent = (LinearLayout) inflater.inflate(R.layout.item_alarm_tag, null);
        alarmValues = parent.findViewById(R.id.alarm_values);
        alarmValues.setText(values);
        return parent;
    }

    public TextView getAlarmValues() {
        return alarmValues;
    }

    public void setAlarmValues(TextView alarmValues) {
        this.alarmValues = alarmValues;
    }

    public String getValues() {
        return alarmValues.getText().toString();
    }

    public void setValues(String values) {
        this.alarmValues.setText(values);
    }
}
