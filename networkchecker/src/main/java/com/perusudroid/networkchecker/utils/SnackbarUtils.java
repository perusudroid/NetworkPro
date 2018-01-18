package com.perusudroid.networkchecker.utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.perusudroid.networkchecker.R;

import java.lang.ref.WeakReference;

public class SnackbarUtils {

    private static WeakReference<Snackbar> snackbarWeakReference;

    private SnackbarUtils() {
        throw new UnsupportedOperationException("u can not instantiate me ...");
    }

    /*  / **
       * Show short snackbar
       *
       * @param parent parent view (or CoordinatorLayout DecorView)
       * @param text text
       * @param textColor text color
       * @param bgColor background color
       * /*/
    public static void showShortSnackbar(View parent, CharSequence text, int textColor, int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor, null, -1, null);
    }

    /* / **
      * Show short snackbar
      *
      * @param parent parent view (or CoordinatorLayout DecorView)
      * @param text text
      * @param textColor text color
      * @param bgColor background color
      * @param actionText event text
      * @param actionTextColor event text color
      * @param listener listener
      * /*/
    public static void showShortSnackbar(View parent, CharSequence text, int textColor, int bgColor,
                                         CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /*   / **
       Show long time snackbar
        *
        * @param parent view (or CoordinatorLayout DecorView)
        * @param text text
        * @param textColor text color
        * @param bgColor background color
        * /*/
    public static void showLongSnackbar(View parent, CharSequence text, int textColor, int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor, null, -1, null);
    }

    /*/ **
            *
    Show long time snackbar
     *
     * @param parent view (or CoordinatorLayout DecorView)
     * @param text text
     * @param textColor text color
     * @param bgColor background color
     * @param actionText event text
     * @param actionTextColor event text color
     * @param listener listener
     * /*/
    public static void showLongSnackbar(View parent, CharSequence text, int textColor, int bgColor,
                                        CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /* / **
      * When displaying custom long snackbar
      *
       * @param parent parent view (or CoordinatorLayout DecorView)
      * @param text text
      * @param duration custom length
      * @param textColor text color
      * @param bgColor background color
      * /*/
    public static void showIndefiniteSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor) {
        showSnackbar(parent, text, duration, textColor, bgColor, null, -1, null);
    }

    /*/ **
    Show custom time snackbar
     *
     * @param parent parent view (or CoordinatorLayout DecorView)
     * @param text text
     * @param duration custom length
     * @param textColor text color
     * @param bgColor background color
     * @param actionText event text
     * @param actionTextColor event text color
     * @param listener listener
     * /*/
    public static void showIndefiniteSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor,
                                              CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, duration, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /* / **
      * Set snackbar text and background colors
      *
      * @param parent parent view (or CoordinatorLayout DecorView)
      * @param text text
      * @param duration display duration
      * @param textColor text color
      * @param bgColor background color
      * @param actionText event text
      * @param actionTextColor event text color
      * @param listener listener
      * /*/
    private static void showSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor,
                                     CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        switch (duration) {
            default:
            case Snackbar.LENGTH_SHORT:
            case Snackbar.LENGTH_LONG:
                snackbarWeakReference = new WeakReference<>(Snackbar.make(parent, text, duration));
                break;
            case Snackbar.LENGTH_INDEFINITE:
                snackbarWeakReference = new WeakReference<>(Snackbar.make(parent, text, Snackbar.LENGTH_INDEFINITE).setDuration(duration));
        }
        View view = snackbarWeakReference.get().getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(textColor);
        view.setBackgroundColor(bgColor);
        if (actionText != null && actionText.length() > 0 && listener != null) {
            snackbarWeakReference.get().setActionTextColor(actionTextColor);
            snackbarWeakReference.get().setAction(actionText, listener);
        }
        snackbarWeakReference.get().show();
    }

    /*  / **
        * Add layout snackbar
       * <P> call </ p> After the show ... Snackbar
       *
       * @param layoutId layout file
       * @param index position (the position at which to add the child or -1 to add last)
       * /*/
    public static void addView(int layoutId, int index) {
        Snackbar snackbar = snackbarWeakReference.get();
        if (snackbar != null) {
            View view = snackbar.getView();
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            layout.addView(child, index, params);
        }
    }

    /* / **
       * Cancel snackbar display
      * /*/
    public static void dismissSnackbar() {
        if (snackbarWeakReference != null && snackbarWeakReference.get() != null) {
            snackbarWeakReference.get().dismiss();
            snackbarWeakReference = null;
        }
    }
}
