package project.ys.glasssystem_r1.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public class BaseBackFragment extends SwipeBackFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
    }

}