package project.ys.glasssystem_r1.ui.fragment.first.child;

import org.androidannotations.annotations.EFragment;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;

@EFragment(R.layout.fragment_alarm)
public class AlarmFragment extends SupportFragment  {
    public static AlarmFragment newInstance() {
        return new AlarmFragment_();
    }


}
