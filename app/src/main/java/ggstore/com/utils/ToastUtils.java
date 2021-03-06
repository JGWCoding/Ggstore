package ggstore.com.utils;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import ggstore.com.BaseApplication;


/**
 * Created by Administrator on 2016/11/29.
 */

public class ToastUtils {
    static Toast ts;
    static Context mContext;
    static boolean mIsShowToast;

    public static void init(Context context, boolean isShowToast){
        mContext = context;
        mIsShowToast = isShowToast;
    }
    public static void showToast(@StringRes int msg){
        showToast(mContext.getString(msg));
    }
    public static void showToast(final String msg){
        if (mContext == null){
            Log.e("showToastError","Please first initialization in the Application");
            return;
        }
        if (!mIsShowToast) return;
        if (Thread.currentThread()== Looper.getMainLooper().getThread()) { //在主线程
            if (ts == null) {
                ts = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
            } else {
                if (msg==null) {
                    ts.setText("给予的字符串是null");
                }else {
                    ts.setText(msg);
                }
                ts.setDuration(Toast.LENGTH_SHORT);
            }


            ts.show();
        }else {
           BaseApplication.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (ts == null) {
                        ts = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
                    } else {
                        ts.setText(msg);
                        ts.setDuration(Toast.LENGTH_SHORT);
                    }
                    ts.show();
                }
            });
        }
    }

    public static void closeToast(){
        if (ts != null) {
            ts.cancel();
        }
    }

}
