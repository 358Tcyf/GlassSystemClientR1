package project.ys.glasssystem_r1.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import project.ys.glasssystem_r1.data.bean.UserBean;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import static project.ys.glasssystem_r1.common.constant.HttpConstant.ADD_USER;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.FILE;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.GET_TAGS;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.LATEST_NO;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.LOGIN;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.RESET_PASSWORD;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.UPDATE_TAGS;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.UPDATE_USER;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.UPLOAD;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.USER;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.USER_DELETE;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.USER_INFO;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.USER_LIST;


public interface HttpContract {
    @POST(USER + LOGIN)
    Observable<RetResult<UserBean>> login(@Query("account") String account,
                                          @Query("password") String password);

    @POST(USER + USER_INFO)
    Observable<RetResult<Map<String, Object>>> userInfo(@Query("account") String account);


    @POST(USER + USER_LIST)
    Observable<RetResult> userList();

    @POST(USER + USER_DELETE)
    Observable<RetResult> deleteUser(@Query("account") String account);

    @POST(USER + LATEST_NO)
    Observable<RetResult> getLatestNo(@Query("roleId") int roleId);

    @POST(USER + ADD_USER)
    Observable<RetResult> addUser(@Query("name") String name,
                                  @Query("no") String no,
                                  @Query("email") String email,
                                  @Query("phone") String phone,
                                  @Query("roleId") int roleId);

    @POST(USER + RESET_PASSWORD)
    Observable<RetResult> resetPassword(@Query("account") String account);

    @POST(USER + UPDATE_USER)
    Observable<RetResult> updateUser(@Query("account") String account,
                                     @Query("email") String email,
                                     @Query("phone") String phone);

    @POST(USER + UPDATE_TAGS)
    Observable<RetResult> updateTags(@Query("account") String account,
                                     @Body List<String> newTags);

    @POST(USER + GET_TAGS)
    Observable<RetResult> getTags(@Query("account") String account);

    @POST(FILE + UPLOAD)
    @Multipart
    Observable<RetResult> upload(@Part MultipartBody.Part file,
                                 @Query("account") String account);
}
