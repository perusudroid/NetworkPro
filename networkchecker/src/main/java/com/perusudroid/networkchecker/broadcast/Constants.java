package com.perusudroid.networkchecker.broadcast;

/**
 * Created by Perusudroid on 09-01-2018.
 */

public class Constants {

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
