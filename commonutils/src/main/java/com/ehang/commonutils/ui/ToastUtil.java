package com.ehang.commonutils.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.SoftReference;

/**
 * 1.在ui线程中显示Toast，不在ui线程会直接post到ui线程中；
 * <p>2.重复调用Toast时会覆盖之前的，不用以队列的方式依次显示
 *
 * @author tom
 */
public class ToastUtil {
    private static Toast toast;
    private static SoftReference<View> viewReference;

    /**
     * 在UI线程显示短时间的Toast
     */
    public static void showShortToast(CharSequence msg) {
        TomApplication.runOnUiThread(() -> showToast(TomApplication.getContext(), msg, Toast.LENGTH_SHORT));
    }

    /**
     * 在UI线程显示长时间的Toast
     */
    public static void showLongToast(CharSequence msg) {
        TomApplication.runOnUiThread(() -> showToast(TomApplication.getContext(), msg, Toast.LENGTH_LONG));
    }

    @SuppressLint("ShowToast")
    private static Toast getToast(Context context) {
        if (viewReference == null) {
            viewReference = new SoftReference<>(Toast.makeText(context, "", Toast.LENGTH_SHORT).getView());
        }
        View view = viewReference.get();
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view != null) {
            toast.setView(view);
            return toast;
        }
        return null;
    }

    private static void showToast(Context context, CharSequence msg,
                                  int duration) {
        try {
            Toast toast = getToast(context);
            if (toast == null) {
                return;
            }
            toast.setText(msg);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
