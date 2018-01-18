package com.perusudroid.myconnection;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.perusudroid.networkchecker.broadcast.INetworkListener;
import com.perusudroid.networkchecker.broadcast.NetworkManager;
import com.perusudroid.networkchecker.utils.AlertDialogUtils;
import com.perusudroid.networkchecker.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements INetworkListener, AlertDialogUtils.AlertDialogListener, RadioGroup.OnCheckedChangeListener {


    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setAssets();
    }


    private void bindViews() {
        radioGroup = findViewById(R.id.rdoGrp);
    }

    private void setAssets() {
        radioGroup.setOnCheckedChangeListener(this);
    }


    private void sampleAllFunctions() {
        //complete
        new NetworkManager.Builder()
                .callback(this) // INetworkListener methods will be trigged on network connection/disconnection
                .showMessage()  // show default toast message to user
                .showCustomMessage(getString(R.string.tst_network_connected), getString(R.string.tst_netword_disconnected)) //show custom toast message
                .showSnack(this) // show default snackbar message
                .showCustomSnack(this, getString(R.string.snk_network_connected), getString(R.string.snk_netword_disconnected), Color.WHITE, Color.BLACK, Color.RED, Color.GREEN) // show custom snackbar message
                .showAlert(this, this) // show default alert message
                .showCustomAlert(this, Constants.networkMsg.DISCONNECTED_ALERT, this) // show custom alert
                .loadPage() // load activity
                .loadCustomPage(Constants.networkMsg.DISCONNECTED_PAGE, false, true) //load activity with custom message
                .build();
    }


    @Override
    public void onNetworkConnected() {
        //replace with your code
        Toast.makeText(this, Constants.networkMsg.CONNECTED_ACTIVITY, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkDisConnected() {
        //replace with your code
        Toast.makeText(this, Constants.networkMsg.DISCONNECTED_ACTIVITY, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveClick(AlertDialog alertDialog, int from) {
        NetworkUtils.showNetworkSettings(MainActivity.this);
    }

    @Override
    public void onNegativeClick(AlertDialog alertDialog, int from) {
        finish();
    }

    @Override
    public void onNeutralClick(AlertDialog alertDialog, int from) {

    }

    /*
       Custom message were commented. If you want to display your own messages, comment default and un-comment custom messages
     */

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {

            case R.id.rdActivity:
                new NetworkManager.Builder()
                        .callback(this) // INetworkListener methods will be trigged on network connection/disconnection
                        .build();
                break;

            case R.id.rdToast:
                new NetworkManager.Builder()
                        .showMessage()  // show default toast message to user
                        // .showCustomMessage(getString(R.string.tst_network_connected), getString(R.string.tst_netword_disconnected)) //show custom toast message
                        .build();
                break;

            case R.id.rdSanck:
                new NetworkManager.Builder()
                        .showSnack(this) // show default snackbar message
                        //.showCustomSnack(this, getString(R.string.snk_network_connected), getString(R.string.snk_netword_disconnected), Color.WHITE, Color.BLACK, Color.RED, Color.GREEN) // show custom snackbar message
                        .build();
                break;
            case R.id.rdAlert:
                new NetworkManager.Builder()
                        // .showAlert(this, this) // show default alert message
                        .showCustomAlert(this, "Network has been disconnected", this) // show custom alert
                        .build();
                break;
            case R.id.rdPage:
                new NetworkManager.Builder()
                        .loadPage() // load activity
                        //.loadCustomPage(Constants.networkMsg.DISCONNECTED_PAGE, false, true) //load activity with custom message
                        .build();
                break;
            default:

        }
    }

}
