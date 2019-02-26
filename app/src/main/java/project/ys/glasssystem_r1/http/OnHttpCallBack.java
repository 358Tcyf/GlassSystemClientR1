package project.ys.glasssystem_r1.http;

public interface OnHttpCallBack<T> {
    void onSuccess(T t);

    void onFailed(String errorMsg);
}