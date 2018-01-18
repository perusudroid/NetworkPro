package com.perusudroid.networkchecker.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


public class ToastUtils {

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;
    private ToastUtils() {
        throw new UnsupportedOperationException("u can not instantiate me ...");
    }

    /*
      * Toast initialization
      *
     When * @param isJumpWhenMore when continuous pop-up toast, toast to pop up a new or modified only text
      * <P> {@ code true}: New toast pop <br> {@code false}: text only modify </ p>
             * <P> {@code false} if it can be used for displaying long toast arbitrary </ p>
             */
    public static void init(boolean isJumpWhenMore) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore;
    }

    /*
       * Safely Show short toast
       *
       * @param context context
       * @param text text
       * */
    public static void showShortToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_SHORT);
            }
        });
    }

    /*
      * Safely Show short toast
      *
      * @param context context
      * @param resId Resource Id
      */
    public static void showShortToastSafe(final Context context, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /*
      * Safely Show short toast
      *
      * @param context context
      * @param resId Resource Id
      * @param args parameter
      */
    public static void showShortToastSafe(final Context context, final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /*
        / **
         * Safely Show short toast
         *
         * @param context context
         * @param format format
         * @param args parameter
         */
    public static void showShortToastSafe(final Context context, final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /*
      * Safely Show short toast
      *
      * @param context context
      * @param text text
      */
    public static void showLongToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_LONG);
            }
        });
    }

    /*
      * Safely Show short toast
      *
      * @param context context
      * @param resId Resource Id
      */
    public static void showLongToastSafe(final Context context, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG);
            }
        });
    }

    /*
      * Safely Show short toast
      *
      * @param context context
      * @param resId Resource Id
      * @param args parameter
      */
    public static void showLongToastSafe(final Context context, final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /*
      * Safely Show short toast
      *
      * @param context context
      * @param format format
      * @param args parameter
      */
    public static void showLongToastSafe(final Context context, final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /*
      * Show short toast
      *
      * @param context context
      * @param text text
      */
    public static void showShortToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /*

        / **
         * Show short toast
         *
         * @param context context
         * @param resId Resource Id
         * /
    */
    public static void showShortToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /*
      * Show short toast
      *
      * @param context context
      * @param resId Resource Id
      * @param args parameter
      */
    public static void showShortToast(Context context, int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_SHORT, args);
    }

    /*
      * Show short toast
      *
      * @param context context
      * @param format format
      * @param args parameter
      */
    public static void showShortToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_SHORT, args);
    }

    /*
             * When displaying long toast
      *
      * @param context context
      * @param text text
      */
    public static void showLongToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /*
             * When displaying long toast
      *
      * @param context context
      * @param resId Resource Id
      */
    public static void showLongToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    /*
             * When displaying long toast
      *
      * @param context context
      * @param resId Resource Id
      * @param args parameter
      */
    public static void showLongToast(Context context, int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_LONG, args);
    }

    /*
             * When displaying long toast
      *
      * @param context context
      * @param format format
      * @param args parameter
      */
    public static void showLongToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_LONG, args);
    }

    /*
             * Display Toast
      *
      * @param context context
      * @param text text
      * @param duration display duration
      */
    private static void showToast(Context context, CharSequence text, int duration) {
        if (isJumpWhenMore) cancelToast();
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /*
             * Display Toast
      *
      * @param context context
      * @param resId Resource Id
      * @param duration display duration
      */
    private static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getResources().getText(resId).toString(), duration);
    }

    /*
             * Display Toast
      *
      * @param context context
      * @param resId Resource Id
      * @param duration display duration
      * @param args parameter
      */
    private static void showToast(Context context, int resId, int duration, Object... args) {
        showToast(context, String.format(context.getResources().getString(resId), args), duration);
    }

    /*
             * Display Toast
      *
      * @param context context
      * @param format format
      * @param duration display duration
      * @param args parameter
      */
    private static void showToast(Context context, String format, int duration, Object... args) {
        showToast(context, String.format(format, args), duration);
    }

    /*
             * Cancel toast display
      */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
