package project.ys.glasssystem_r1.http;

import io.reactivex.Completable;
import io.reactivex.Observable;
import project.ys.glasssystem_r1.bean.UserBean;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static project.ys.glasssystem_r1.constant.HttpConstant.ADD_USER;
import static project.ys.glasssystem_r1.constant.HttpConstant.LATEST_NO;
import static project.ys.glasssystem_r1.constant.HttpConstant.USER;
import static project.ys.glasssystem_r1.constant.HttpConstant.LOGIN;
import static project.ys.glasssystem_r1.constant.HttpConstant.USER_DELETE;
import static project.ys.glasssystem_r1.constant.HttpConstant.USER_LIST;


public interface HttpContract {
    @POST(USER + LOGIN)
    Observable<RetResult<UserBean>> login(@Query("account") String account,
                                          @Query("password") String password);

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
}
