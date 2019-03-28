package project.ys.glasssystem_r1.http;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;

import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.INSTANT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.URL;
import static project.ys.glasssystem_r1.ui.fragment.first.child.ChartsFragment.PUSH;

@Rest(rootUrl = HTTP + URL + PORT, converters = MyConverter.class)
public interface MyRestClient {

    @Get(PUSH + INSTANT + "/{alias}")
    void getInstantPushWithAlias(@Path String alias);

    @Get(PUSH + INSTANT)
    void getInstantPush();
}
