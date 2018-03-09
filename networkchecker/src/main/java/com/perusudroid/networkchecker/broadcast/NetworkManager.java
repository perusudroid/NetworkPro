package com.perusudroid.networkchecker.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.perusudroid.networkchecker.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Perusudroid on 08-01-2018.
 */

public class NetworkManager extends BroadcastReceiver {

    private static final String TAG = NetworkManager.class.getSimpleName();
    private static INetworkListener iNetworkListener;
    private static INetworkProDialogListener alertDialogListener;
    private static WeakReference<View> mParentView;
    private static Activity alertContext;
    private static NetworkMsg networkMsg;
    private static WeakReference<Snackbar> snackbarWeakReference;
    private static AlertDialog alertDialog;

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void showNetworkSettings(Context mContext) {
        Intent chooserIntent = Intent.createChooser(getSettingsIntent(Settings.ACTION_DATA_ROAMING_SETTINGS),
                "Complete action using");
        List<Intent> networkIntents = new ArrayList<>();
        networkIntents.add(getSettingsIntent(Settings.ACTION_WIFI_SETTINGS));
        networkIntents.add(getSettingsIntent(Settings.ACTION_WIFI_SETTINGS));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, networkIntents.toArray(new Parcelable[]{}));
        chooserIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivityBySettings(mContext, chooserIntent);
    }

    private static Intent getSettingsIntent(String settings) {
        return new Intent(settings);
    }

    private static void startActivityBySettings(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private static void dismissSnackbar() {
        if (snackbarWeakReference != null && snackbarWeakReference.get() != null) {
            snackbarWeakReference.get().dismiss();
            snackbarWeakReference = null;
        }
    }

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

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            if (intent.getBooleanExtra(Constants.common.isDefault, false)) {
                pushToSubscribers(context);
            }
        }
    }

    private void pushToSubscribers(final Context context) {


        if (isNetworkAvailable(context)) {
            //connected
            if (iNetworkListener != null) {
                iNetworkListener.onNetworkConnected();
            }

            if (networkMsg != null) {
                if (networkMsg.isShowAlert()) {
                    dismissDialog();
                }
                if (networkMsg.isShowPage()) {
                    Intent intent = new Intent(Constants.bundles.finishActivity);
                    context.sendBroadcast(intent);
                }
                if (networkMsg.isShowToast()) {
                    Toast.makeText(context, networkMsg.getToastConnectedMsg(), Toast.LENGTH_SHORT).show();
                }
                if (networkMsg.isShowSnack()) {
                    showSnackbar(mParentView.get().getRootView(), networkMsg.getSnackConnectedMsg(), 3000,
                            networkMsg.getSnackConnectedTextColor(),
                            networkMsg.getSnackBackgroundColor(),
                            Constants.common.actionTxt,
                            networkMsg.getSnackActionTextColor(), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dismissSnackbar();
                                }
                            });
                }
            }
        } else {
            //disconnected
            if (iNetworkListener != null) {
                iNetworkListener.onNetworkDisConnected();
            }
            if (networkMsg != null) {
                if (networkMsg.isShowPage()) {
                    Intent intent = new Intent(context, NoInternetActivity.class);
                    intent.putExtra(Constants.bundles.settings, networkMsg.isSettingsEnabled());
                    intent.putExtra(Constants.bundles.retry, networkMsg.isRetryEnabled());
                    intent.putExtra(Constants.bundles.networkMsg, networkMsg.getNetworkMessage());
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if (networkMsg.isShowAlert()) {
                    showAlertDialog(alertContext,
                            networkMsg.getAlertDisconnectedMsg(),
                            networkMsg.getAlertDisConnectedMsg(),
                            "Settings",
                            "Exit",
                            0,
                            false,
                            alertDialogListener);
                }
                if (networkMsg.isShowToast()) {
                    Toast.makeText(context, networkMsg.getToastDisConnectedMsg(), Toast.LENGTH_SHORT).show();
                }
                if (networkMsg.isShowSnack()) {

                    showSnackbar(mParentView.get().getRootView(),
                            networkMsg.getSnackDisConnectedMsg(),
                            10000,
                            networkMsg.getSnackDisconnectedTextColor(),
                            networkMsg.getSnackBackgroundColor(),
                            Constants.common.actionSettings,
                            networkMsg.getSnackActionTextColor(),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showNetworkSettings(context);
                                }
                            });
                }
            }
        }
    }

    public void showAlertDialog(final Activity context, String title, String message, String positive, String negative, final int from, final boolean isCancelable, final INetworkProDialogListener alertDialogListener) {

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

                            if (alertDialogListener != null) {
                                showNetworkSettings(context);
                                //alertDialogListener.onPositiveClick(alertDialog, from);
                            }
                            //alertDialog.dismiss();
                        }
                    });
        }
        if (!TextUtils.isEmpty(negative)) {
            alertDialogBuilder.setNegativeButton(negative,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (alertDialogListener != null) {
                                alertDialogListener.onNegativeClick(alertDialog, from);
                            }
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
                    try {
                        alertDialog.show();
                    } catch (Exception e) {

                    }
                    Looper.loop();
                }
            }.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static class Builder {

        private INetworkListener iNetworkListener;
        private INetworkProDialogListener alertDialogListener;
        private NetworkMsg networkMsg = new NetworkMsg();
        private View mParentView;
        private Activity activity;

        public Builder() {

        }


        @NonNull
        public Builder checkonStart(Activity activity, INetworkProDialogListener alertDialogListener) {
            Log.d(TAG, "checkonStart: "+ !isNetworkAvailable(activity));
            if(!isNetworkAvailable(activity)){
                this.activity = activity;
                this.alertDialogListener = alertDialogListener;
                networkMsg.setShowAlert(true);
                networkMsg.setAlertDisconnectedMsg(Constants.networkMsg.DISCONNECTED);
            }

            return this;
        }

        /**
         * Implement INetworkListener interface and pass to callback
         * <p>
         *
         * @param iNetworkListener
         */
        @NonNull
        public Builder callback(INetworkListener iNetworkListener) {
            this.iNetworkListener = iNetworkListener;
            return this;
        }


        /**
         * Show alert dialog
         * <p>
         *
         * @param context             - Activity's context
         * @param alertDialogListener - activity where INetworkProDialogListener is implemented
         */
        @NonNull
        public Builder showAlert(Context context, INetworkProDialogListener alertDialogListener) {
            this.activity = (Activity) context;
            this.alertDialogListener = alertDialogListener;
            networkMsg.setShowAlert(true);
            networkMsg.setAlertDisconnectedMsg(Constants.networkMsg.DISCONNECTED);
            return this;
        }

        /**
         * Show alert dialog with your own message
         * <p>
         *
         * @param context             - Activity's context
         * @param alertMsg            - Your own message to show on network disconnected
         * @param alertDialogListener - activity where INetworkProDialogListener is implemented
         */
        @NonNull
        public Builder showCustomAlert(Context context, String alertMsg, INetworkProDialogListener alertDialogListener) {
            this.activity = (Activity) context;
            this.alertDialogListener = alertDialogListener;
            networkMsg.setShowAlert(true);
            networkMsg.setAlertDisconnectedMsg(alertMsg);
            return this;
        }


        /**
         * Show toast message on network connected/disconnected
         * <p>
         **/
        @NonNull
        public Builder showMessage() {
            networkMsg.setShowToast(true);
            networkMsg.setToastConnectedMsg(Constants.networkMsg.CONNECTED);
            networkMsg.setToastDisConnectedMsg(Constants.networkMsg.DISCONNECTED);
            return this;
        }

        /**
         * Show custom toast message on network connected/disconnected
         * <p>
         *
         * @param connectedMsg    - Your network connected message
         * @param disconnectedMsg - Your network disconnected message
         **/
        @NonNull
        public Builder showCustomMessage(String connectedMsg, String disconnectedMsg) {
            networkMsg.setShowToast(true);
            networkMsg.setToastConnectedMsg(connectedMsg);
            networkMsg.setToastDisConnectedMsg(disconnectedMsg);
            return this;
        }


        /**
         * Show snackbar message on network connected/disconnected
         * <p>
         *
         * @param mContext - Activity's context
         **/
        @NonNull
        public Builder showSnack(Context mContext) {
            Activity activity = (AppCompatActivity) mContext;
            this.mParentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            networkMsg.setShowSnack(true);
            networkMsg.setSnackConnectedMsg(Constants.networkMsg.CONNECTED);
            networkMsg.setSnackDisConnectedMsg(Constants.networkMsg.DISCONNECTED);
            networkMsg.setSnackBackgroundColor(Color.WHITE);
            networkMsg.setSnackActionTextColor(Color.BLACK);
            networkMsg.setSnackConnectedTextColor(Color.GRAY);
            networkMsg.setSnackDisconnectedTextColor(Color.RED);
            return this;
        }


        /**
         * Show custom snackbar message on network connected/disconnected
         * <p>
         *
         * @param mContext              - Activity's context
         * @param connectedMsg          - Network connected message
         * @param disconnectedMsg       - Network disconnected message
         * @param backgroundColor       - Snackbar background color
         * @param connectedTextColor    - Network connected text color
         * @param disconnectedTextColor - Network disconnected text color
         * @param snackActionTextColor  - Snackbar's action text color
         **/
        @NonNull
        public Builder showCustomSnack(Context mContext, String connectedMsg, String disconnectedMsg, int backgroundColor, int connectedTextColor, int disconnectedTextColor, int snackActionTextColor) {
            Activity activity = (AppCompatActivity) mContext;
            this.mParentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            networkMsg.setShowSnack(true);
            networkMsg.setSnackConnectedMsg(connectedMsg);
            networkMsg.setSnackDisConnectedMsg(disconnectedMsg);
            networkMsg.setSnackBackgroundColor(backgroundColor);
            networkMsg.setSnackActionTextColor(snackActionTextColor);
            networkMsg.setSnackConnectedTextColor(connectedTextColor);
            networkMsg.setSnackDisconnectedTextColor(disconnectedTextColor);
            return this;
        }

        /**
         * Load page on network disconnected
         * <p>
         **/
        @NonNull
        public Builder loadPage() {
            networkMsg.setShowPage(true);
            networkMsg.setNetworkMessage(Constants.networkMsg.NO_NETWORK);
            networkMsg.setRetryEnabled(true);
            networkMsg.setSettingsEnabled(true);
            return this;
        }

        /**
         * Load page with custom text on network disconnected
         * <p>
         *
         * @param networkMessage  - Network msg to show on disconnection
         * @param retryEnabled    - Enable retry button
         * @param settingsEnabled - Enable settings button
         **/
        @NonNull
        public Builder loadCustomPage(String networkMessage, boolean retryEnabled, boolean settingsEnabled) {
            networkMsg.setShowPage(true);
            networkMsg.setNetworkMessage(networkMessage);
            networkMsg.setRetryEnabled(retryEnabled);
            networkMsg.setSettingsEnabled(settingsEnabled);
            return this;
        }

        /*
         * You have nothing to deal below!
         */

        @NonNull
        public NetworkManager build() {
            NetworkManager networkManager = new NetworkManager();
            NetworkManager.iNetworkListener = iNetworkListener;
            NetworkManager.alertDialogListener = alertDialogListener;
            NetworkManager.mParentView = new WeakReference<>(mParentView);
            NetworkManager.alertContext = activity;
            NetworkManager.networkMsg = networkMsg;
            if(!isNetworkAvailable(activity)){
                networkManager.pushToSubscribers(activity);
            }
            return networkManager;
        }

    }

    static class Constants {


        interface myString {
            String network_connected = "Network is sconnected";
            String tst_netword_disconnected = "No network connection";
            String error_no_internet = "No Internet connection. Make sure that WiFi or cellular mobile data is turned on, then try again";
            String network_disconnected = "Network is disconnected";
            String retry = "Retry";
            String settings = "Settings";
        }

        interface common {
            String actionTxt = "Ok";
            String actionSettings = "Settings";
            String isDefault = "isDefault";
        }

        interface bundles {
            String finishActivity = "finish_activity";
            String settings = "BUNDLE_SETTINGS";
            String retry = "BUNDLE_RETRY";
            String networkMsg = "BUNDLE_NETWORK";
        }

        interface networkMsg {
            String CONNECTED = "Network is connected";
            String DISCONNECTED = "Internet is not enabled. Do you want to go to settings menu?";
            String NO_NETWORK = "No Internet connection. Make sure that WiFi or cellular mobile data is turned on, then try again";
        }

    }

}
