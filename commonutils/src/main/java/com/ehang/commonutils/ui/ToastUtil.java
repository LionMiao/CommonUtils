package com.ehang.commonutils.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.SoftReference;

public class ToastUtil {
    private static Toast toast;
    private static SoftReference<View> viewReference;

    private ToastUtil() {
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

    public static void showShortToast(Context context, CharSequence msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }


    public static void showLongToast(Context context, CharSequence msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, CharSequence msg,
                                  int duration) {
        try {
            getToast(context);
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
