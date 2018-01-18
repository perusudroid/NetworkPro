package com.perusudroid.networkchecker.broadcast;

/**
 * Created by Perusudroid on 09-01-2018.
 */

public class NetworkDisplay {

    private boolean showToast = false;
    private boolean showAlert = false;
    private boolean showSnack = false;
    private boolean showPage = false;
    private boolean settingsEnabled = false;
    private boolean retryEnabled = false;
    private String toastConnectedMsg;
    private String snackConnectedMsg;
    private String toastDisConnectedMsg;
    private String snackDisConnectedMsg;
    private String alertDisConnectedMsg;
    private String networkMessage;
    private int snackBackgroundColor = 0;
    private int snackConnectedTextColor = 0;
    private int snackDisconnectedTextColor = 0;
    private int snackActionTextColor = 0;
    private String alertDisconnectedMsg;


    public String getAlertDisConnectedMsg() {
        return alertDisConnectedMsg;
    }

    public void setAlertDisConnectedMsg(String alertDisConnectedMsg) {
        this.alertDisConnectedMsg = alertDisConnectedMsg;
    }

    public String getAlertDisconnectedMsg() {
        return alertDisconnectedMsg;
    }

    public void setAlertDisconnectedMsg(String alertDisconnectedMsg) {
        this.alertDisconnectedMsg = alertDisconnectedMsg;
    }

    public boolean isShowAlert() {
        return showAlert;
    }

    public void setShowAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }

    public String getNetworkMessage() {
        return networkMessage;
    }

    public void setNetworkMessage(String networkMessage) {
        this.networkMessage = networkMessage;
    }

    public boolean isSettingsEnabled() {
        return settingsEnabled;
    }

    public void setSettingsEnabled(boolean settingsEnabled) {
        this.settingsEnabled = settingsEnabled;
    }

    public boolean isRetryEnabled() {
        return retryEnabled;
    }

    public void setRetryEnabled(boolean retryEnabled) {
        this.retryEnabled = retryEnabled;
    }

    public boolean isShowPage() {
        return showPage;
    }

    public void setShowPage(boolean showPage) {
        this.showPage = showPage;
    }

    public int getSnackDisconnectedTextColor() {
        return snackDisconnectedTextColor;
    }

    public void setSnackDisconnectedTextColor(int snackDisconnectedTextColor) {
        this.snackDisconnectedTextColor = snackDisconnectedTextColor;
    }

    public int getSnackActionTextColor() {
        return snackActionTextColor;
    }

    public void setSnackActionTextColor(int snackActionTextColor) {
        this.snackActionTextColor = snackActionTextColor;
    }

    public int getSnackBackgroundColor() {
        return snackBackgroundColor;
    }

    public void setSnackBackgroundColor(int snackBackgroundColor) {
        this.snackBackgroundColor = snackBackgroundColor;
    }

    public int getSnackConnectedTextColor() {
        return snackConnectedTextColor;
    }

    public void setSnackConnectedTextColor(int snackConnectedTextColor) {
        this.snackConnectedTextColor = snackConnectedTextColor;
    }

    public void setConSnackTextColor(int snackTextColor) {
        this.snackConnectedTextColor = snackTextColor;
    }

    public boolean isShowSnack() {
        return showSnack;
    }

    public void setShowSnack(boolean showSnack) {
        this.showSnack = showSnack;
    }

    public String getSnackConnectedMsg() {
        return snackConnectedMsg;
    }

    public void setSnackConnectedMsg(String snackConnectedMsg) {
        this.snackConnectedMsg = snackConnectedMsg;
    }

    public String getSnackDisConnectedMsg() {
        return snackDisConnectedMsg;
    }

    public void setSnackDisConnectedMsg(String snackDisConnectedMsg) {
        this.snackDisConnectedMsg = snackDisConnectedMsg;
    }

    public boolean isShowToast() {
        return showToast;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public String getToastConnectedMsg() {
        return toastConnectedMsg;
    }

    public void setToastConnectedMsg(String toastConnectedMsg) {
        this.toastConnectedMsg = toastConnectedMsg;
    }

    public String getToastDisConnectedMsg() {
        return toastDisConnectedMsg;
    }

    public void setToastDisConnectedMsg(String toastDisConnectedMsg) {
        this.toastDisConnectedMsg = toastDisConnectedMsg;
    }
}
