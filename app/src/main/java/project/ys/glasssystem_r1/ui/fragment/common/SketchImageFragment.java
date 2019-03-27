package project.ys.glasssystem_r1.ui.fragment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import me.panpf.sketch.SketchImageView;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;

import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;
import static project.ys.glasssystem_r1.common.constant.UserConstant.USER_PIC_PATH;
import static project.ys.glasssystem_r1.util.utils.DateUtils.getNowTime;

@EFragment
public class SketchImageFragment extends BaseBackFragment {


    public static SketchImageFragment newInstance() {
        return new SketchImageFragment_();
    }

    public static SketchImageFragment newInstance(String picPath) {
        Bundle args = new Bundle();
        args.putString(USER_PIC_PATH, picPath);
        SketchImageFragment fragment = new SketchImageFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.sketch_image)
    SketchImageView sketchImageView;
    private String picPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sketch_image, container, false);
        return attachToSwipeBack(view);
    }

    @AfterInject
    void afterInject() {
        picPath = getArguments().getString(USER_PIC_PATH);
    }

    @AfterViews
    void afterViews() {
        sketchImageView.setZoomEnabled(true);
        sketchImageView.getOptions()
                .setErrorImage(R.drawable.ic_error)
                .setLoadingImage(R.drawable.ic_loading);
        sketchImageView.displayImage(HTTP + getURL() + PORT + picPath + "/" + getNowTime());
    }
}
