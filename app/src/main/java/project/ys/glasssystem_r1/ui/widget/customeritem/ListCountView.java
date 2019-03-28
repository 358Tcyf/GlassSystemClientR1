package project.ys.glasssystem_r1.ui.widget.customeritem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.ys.glasssystem_r1.R;

public class ListCountView {

    public static RelativeLayout parent;

    public static TextView listCount;


    public ListCountView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        parent = (RelativeLayout) inflater.inflate(R.layout.head_count, null);
        listCount = parent.findViewById(R.id.list_count);
    }

    public View createItem() {
        return parent;
    }


    public void setCount(int count) {
        listCount.setText("共" + count + "条");
    }
}
