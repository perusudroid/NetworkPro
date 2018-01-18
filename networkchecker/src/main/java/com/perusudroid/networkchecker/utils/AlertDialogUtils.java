package com.perusudroid.networkchecker.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Perusudroid on 09-01-2018.
 */

public class AlertDialogUtils {

    private static AlertDialogUtils mIAlertDialogUtils;
    private static AlertDialog alertDialog;
    private boolean isShowing = false;

    public AlertDialogUtils() {
    }

    public static AlertDialogUtils getInstance() {
        if (mIAlertDialogUtils == null) {
            mIAlertDialogUtils = new AlertDialogUtils();
        }
        return mIAlertDialogUtils;
    }

    public static AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public static void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /**
     * Displays the AlertDialog with 3 Action buttons
     * <p>
     * you can set cancelable property
     *
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param neutral
     * @param from
     * @param isCancelable
     */
    public void showAlertDialog(Activity context, String title, String message, String positive, String negative, String neutral, final int from, final boolean isCancelable, final WeakReference<AlertDialogListener> alertDialogListener) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title))
            alertDialogBuilder.setTitle(title);
        if (!TextUtils.isEmpty(message))
            alertDialogBuilder.setMessage(message);

        if (!TextUtils.isEmpty(positive)) {
            alertDialogBuilder.setPositiveButton(positive,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            alertDialogListener.get().onPositiveClick(alertDialog, from);
                            //alertDialog.dismiss();
                        }
                    });
        }
        if (!TextUtils.isEmpty(neutral)) {
            alertDialogBuilder.setNeutralButton(neutral,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialogListener.get().onNeutralClick(alertDialog, from);
                            //alertDialog.dismiss();
                        }
                    });
        }
        if (!TextUtils.isEmpty(negative)) {
            alertDialogBuilder.setNegativeButton(negative,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialogListener.get().onNegativeClick(alertDialog, from);
                            // alertDialog.dismiss();
                        }
                    });
        }

        try {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.setCancelable(isCancelable);
                   try{
                       alertDialog.show();
                   }catch (Exception e){

                   }
                    Looper.loop();
                }
            }.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    /**
     * Displays the AlertDialog with positive action button only
     * <p>
     * you can set cancelable property
     *
     * @param title
     * @param message
     * @param positive
     * @param from
     * @param isCancelable
     */

    public void showAlertDialog(Activity activity, String title, String message, String positive, final int from, boolean isCancelable, WeakReference<AlertDialogListener> alertDialogListener) {
        showAlertDialog(activity, title, message, positive, "", "", from, isCancelable, alertDialogListener);
    }

    /**
     * Displays the AlertDialog with positive action button only
     * <p>
     * cancelable property is false (Default)
     *
     * @param title
     * @param message
     * @param positive
     * @param from
     */
    public void showAlertDialog(Activity activity, String title, String message, String positive, final int from, WeakReference<AlertDialogListener> alertDialogListener) {
        showAlertDialog(activity, title, message, positive, "", "", from, false, alertDialogListener);
    }

    /**
     * Displays the AlertDialog with positive & negative buttons
     * <p>
     * you can set cancelable property
     *
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param from
     * @param isCancelable
     */

    public void showAlertDialog(Activity activity, String title, String message, String positive, String negative, final int from, boolean isCancelable, WeakReference<AlertDialogListener> alertDialogListener) {
        showAlertDialog(activity, title, message, positive, negative, "", from, isCancelable, alertDialogListener);
    }

    /**
     * Displays the AlertDialog with positive & negative buttons
     * <p>
     * cancelable property is false (Default)
     *
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param from
     */
    public void showAlertDialog(Activity activity, String title, String message, String positive, String negative, final int from, WeakReference<AlertDialogListener> alertDialogListener) {
        showAlertDialog(activity, title, message, positive, negative, "", from, false, alertDialogListener);
    }

    /**
     * Displays the AlertDialog with 3 Action buttons
     * <p>
     * cancelable property is false (Default)
     *
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param neutral
     * @param from
     */
    public void showAlertDialog(Activity activity, String title, String message, String positive, String negative, String neutral, final int from, WeakReference<AlertDialogListener> alertDialogListener) {
        showAlertDialog(activity, title, message, positive, negative, neutral, from, false, alertDialogListener);
    }


    public interface AlertDialogListener {
        void onPositiveClick(AlertDialog alertDialog, int from);

        void onNegativeClick(AlertDialog alertDialog, int from);

        void onNeutralClick(AlertDialog alertDialog, int from);
    }

}