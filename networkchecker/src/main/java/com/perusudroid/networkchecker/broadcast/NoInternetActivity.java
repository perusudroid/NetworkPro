package com.perusudroid.networkchecker.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.perusudroid.networkchecker.R;
import com.perusudroid.networkchecker.utils.NetworkUtils;

public class NoInternetActivity extends AppCompatActivity {

    String DO_FINISH = "finish_activity";
    BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            String action = intent.getAction();
            if (action.equals(DO_FINISH)) {
                finish();
            }
        }
    };
    private Button btnRetry, btnSettings;
    private TextView tvNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_no_internet);
        bindViews();
        getInputs(getIntent().getExtras());
    }

    private void bindViews() {
        btnRetry = findViewById(R.id.btnRetry);
        btnSettings = findViewById(R.id.btnSettings);
        tvNoInternet = findViewById(R.id.tvNoInternet);
    }

    private void getInputs(Bundle extras) {
        if (extras != null) {
            if (extras.getBoolean(NetworkManager.Constants.bundles.settings)) {
                if (btnSettings.getVisibility() == View.GONE) {
                    btnSettings.setVisibility(View.VISIBLE);
                }
            } else {
                if (btnSettings.getVisibility() == View.VISIBLE) {
                    btnSettings.setVisibility(View.GONE);
                }
            }
            if (extras.getBoolean(NetworkManager.Constants.bundles.retry)) {
                if (btnRetry.getVisibility() == View.GONE) {
                    btnRetry.setVisibility(View.VISIBLE);
                }
            } else {
                if (btnRetry.getVisibility() == View.VISIBLE) {
                    btnRetry.setVisibility(View.GONE);
                }
            }

            if (extras.getString(NetworkManager.Constants.bundles.networkMsg) != null) {
                tvNoInternet.setText(extras.getString(NetworkManager.Constants.bundles.networkMsg));
            } else {
                tvNoInternet.setText(NetworkManager.Constants.myString.error_no_internet);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcast_reciever, new IntentFilter("finish_activity"));
    }

    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast_reciever);
    }

    public void retryClicked(View view) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            finish();
        }
    }

    public void settingsClicked(View view) {
        NetworkUtils.showNetworkSettings(this);
    }
}
