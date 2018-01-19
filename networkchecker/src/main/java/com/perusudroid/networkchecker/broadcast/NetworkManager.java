package com.perusudroid.networkchecker.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.perusudroid.networkchecker.utils.AlertDialogUtils;
import com.perusudroid.networkchecker.utils.NetworkUtils;
import com.perusudroid.networkchecker.utils.SnackbarUtils;
import com.perusudroid.networkchecker.utils.ToastUtils;

import java.lang.ref.WeakReference;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Perusudroid on 08-01-2018.
 */

public class NetworkManager extends BroadcastReceiver {

    private static final String TAG = NetworkManager.class.getSimpleName();
    private static WeakReference<INetworkListener> iNetworkListener;
    private static WeakReference<AlertDialogUtils.AlertDialogListener> alertDialogListener;
    private static WeakReference<View> mParentView;
    private static Activity alertContext;
    private static NetworkDisplay networkDisplay;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            if (intent.getBooleanExtra(Constants.common.isDefault, false)) {
                pushToSubscribers(context);
            }
        }
    }

    private void pushToSubscribers(final Context context) {


        if (NetworkUtils.isNetworkAvailable(context)) {
            //connected
            if (iNetworkListener != null) {
                iNetworkListener.get().onNetworkConnected();
            }

            if (networkDisplay != null) {
                if (networkDisplay.isShowAlert()) {
                    AlertDialogUtils.dismissDialog();
                }
                if (networkDisplay.isShowPage()) {
                    Intent intent = new Intent(Constants.bundles.finishActivity);
                    context.sendBroadcast(intent);
                }
                if (networkDisplay.isShowToast()) {
                    ToastUtils.showShortToastSafe(context, networkDisplay.getToastConnectedMsg());
                }
                if (networkDisplay.isShowSnack()) {
                    SnackbarUtils.showIndefiniteSnackbar(mParentView.get().getRootView(), networkDisplay.getSnackConnectedMsg(), 3000,
                            networkDisplay.getSnackConnectedTextColor(),
                            networkDisplay.getSnackBackgroundColor(),
                            Constants.common.actionTxt,
                            networkDisplay.getSnackActionTextColor(), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SnackbarUtils.dismissSnackbar();
                                }
                            });
                }
            }
        } else {
            //disconnected
            if (iNetworkListener != null) {
                iNetworkListener.get().onNetworkDisConnected();
            }
            if (networkDisplay != null) {
                if (networkDisplay.isShowPage()) {
                    Intent intent = new Intent(context, NoInternetActivity.class);
                    intent.putExtra(Constants.bundles.settings, networkDisplay.isSettingsEnabled());
                    intent.putExtra(Constants.bundles.retry, networkDisplay.isRetryEnabled());
                    intent.putExtra(Constants.bundles.networkMsg, networkDisplay.getNetworkMessage());
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if (networkDisplay.isShowAlert()) {
                    AlertDialogUtils.getInstance().showAlertDialog(alertContext, networkDisplay.getAlertDisconnectedMsg(), networkDisplay.getAlertDisConnectedMsg(), "Settings", "Exit", 0, false, alertDialogListener);
                }
                if (networkDisplay.isShowToast()) {
                    ToastUtils.showShortToastSafe(context, networkDisplay.getToastDisConnectedMsg());
                }
                if (networkDisplay.isShowSnack()) {
                    SnackbarUtils.showIndefiniteSnackbar(mParentView.get().getRootView(),
                            networkDisplay.getSnackDisConnectedMsg(),
                            10000,
                            networkDisplay.getSnackDisconnectedTextColor(),
                            networkDisplay.getSnackBackgroundColor(),
                            Constants.common.actionSettings,
                            networkDisplay.getSnackActionTextColor(),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    NetworkUtils.showNetworkSettings(context);
                                }
                            });
                }
            }
        }
    }


    public static class Builder {

        private WeakReference<INetworkListener> iNetworkListener;
        private WeakReference<AlertDialogUtils.AlertDialogListener> alertDialogListener;
        private NetworkDisplay networkDisplay = new NetworkDisplay();
        private View mParentView;
        private Activity activity;

        /**
         * Implement INetworkListener interface and pass to callback
         * <p>
         *
         * @param iNetworkListener
         */
        @NonNull
        public Builder callback(INetworkListener iNetworkListener) {
            this.iNetworkListener = new WeakReference<>(iNetworkListener);
            return this;
        }


        /**
         * Show alert dialog
         * <p>
         *
         * @param context             - Activity's context
         * @param alertDialogListener - activity where AlertDialogUtils.AlertDialogListener is implemented
         */
        @NonNull
        public Builder showAlert(Context context, AlertDialogUtils.AlertDialogListener alertDialogListener) {
            this.activity = (Activity) context;
            this.alertDialogListener = new WeakReference<>(alertDialogListener);
            networkDisplay.setShowAlert(true);
            networkDisplay.setAlertDisconnectedMsg(Constants.networkMsg.DISCONNECTED);
            return this;
        }

        /**
         * Show alert dialog with your own message
         * <p>
         *
         * @param context             - Activity's context
         * @param alertMsg            - Your own message to show on network disconnected
         * @param alertDialogListener - activity where AlertDialogUtils.AlertDialogListener is implemented
         */
        @NonNull
        public Builder showCustomAlert(Context context, String alertMsg, AlertDialogUtils.AlertDialogListener alertDialogListener) {
            this.activity = (Activity) context;
            this.alertDialogListener = new WeakReference<>(alertDialogListener);
            networkDisplay.setShowAlert(true);
            networkDisplay.setAlertDisconnectedMsg(alertMsg);
            return this;
        }


        /**
         * Show toast message on network connected/disconnected
         * <p>
         **/
        @NonNull
        public Builder showMessage() {
            networkDisplay.setShowToast(true);
            networkDisplay.setToastConnectedMsg(Constants.networkMsg.CONNECTED);
            networkDisplay.setToastDisConnectedMsg(Constants.networkMsg.DISCONNECTED);
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
            networkDisplay.setShowToast(true);
            networkDisplay.setToastConnectedMsg(connectedMsg);
            networkDisplay.setToastDisConnectedMsg(disconnectedMsg);
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
            networkDisplay.setShowSnack(true);
            networkDisplay.setSnackConnectedMsg(Constants.networkMsg.CONNECTED);
            networkDisplay.setSnackDisConnectedMsg(Constants.networkMsg.DISCONNECTED);
            networkDisplay.setSnackBackgroundColor(Color.WHITE);
            networkDisplay.setSnackActionTextColor(Color.BLACK);
            networkDisplay.setSnackConnectedTextColor(Color.GRAY);
            networkDisplay.setSnackDisconnectedTextColor(Color.RED);
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
            networkDisplay.setShowSnack(true);
            networkDisplay.setSnackConnectedMsg(connectedMsg);
            networkDisplay.setSnackDisConnectedMsg(disconnectedMsg);
            networkDisplay.setSnackBackgroundColor(backgroundColor);
            networkDisplay.setSnackActionTextColor(snackActionTextColor);
            networkDisplay.setSnackConnectedTextColor(connectedTextColor);
            networkDisplay.setSnackDisconnectedTextColor(disconnectedTextColor);
            return this;
        }

        /**
         * Load page on network disconnected
         * <p>
         **/
        @NonNull
        public Builder loadPage() {
            networkDisplay.setShowPage(true);
            networkDisplay.setNetworkMessage(Constants.networkMsg.NO_NETWORK);
            networkDisplay.setRetryEnabled(true);
            networkDisplay.setSettingsEnabled(true);
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
            networkDisplay.setShowPage(true);
            networkDisplay.setNetworkMessage(networkMessage);
            networkDisplay.setRetryEnabled(retryEnabled);
            networkDisplay.setSettingsEnabled(settingsEnabled);
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
            NetworkManager.networkDisplay = networkDisplay;
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
            String DISCONNECTED = "Network is disconnected";
            String NO_NETWORK = "No Internet connection. Make sure that WiFi or cellular mobile data is turned on, then try again";
        }

    }

}
