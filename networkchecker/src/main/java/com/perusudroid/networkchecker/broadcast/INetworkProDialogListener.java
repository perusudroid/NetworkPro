package com.perusudroid.networkchecker.broadcast;

import android.support.v7.app.AlertDialog;

/**
 * Created by Intel on 09-03-2018.
 */

public interface INetworkProDialogListener {

    void onPositiveClick(AlertDialog alertDialog, int from);

    void onNegativeClick(AlertDialog alertDialog, int from);

    void onNeutralClick(AlertDialog alertDialog, int from);
}