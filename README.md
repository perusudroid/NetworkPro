# NetworkPro
Simple library to handle network connections in different views in just few lines of code. Primary goal of this library is to simplify the overall process in few lines of code. More options and customization available in code.

  [![Watch the video](https://github.com/Periyanayagam/NetworkPro/blob/master/networkPro.gif)](https://github.com/Periyanayagam/NetworkPro/blob/master/networkPro.gif)
  
  # Usage
  
  ### First and foremost in AndroidManifest file register broadcast receiver
   ```
   <receiver
            android:name="com.perusudroid.networkchecker.broadcast.NetworkManager"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
                <action android:name="android.net.wifi.WIFI_STATE_CHANGED" />
            </intent-filter>
        </receiver>
   ```
  
  <b>Make sure to use permission</b>
 ```
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
 ```
  
   <b>To handle network connections within activity</b>
  
  ```
  new NetworkManager.Builder()
                        .callback(this) // INetworkListener methods will be trigged on network connection/disconnection
                        .build();
```
                        
<b>To show toast message</b>
```
         new NetworkManager.Builder()
             .showMessage()  // show default toast message to user
              .build();
```
         
          
 <b>To show toast with custom message</b>
  ```
  new NetworkManager.Builder()
             .showCustomMessage(getString(R.string.tst_network_connected), getString(R.string.tst_netword_disconnected)) 
             .build();
 ```
 
<b>To show snackbar</b>

```
         new NetworkManager.Builder()
              .showSnack(this) // show default snackbar message
              .build();
```      
           
 <b>To show snackbar with custom message</b>
 ```
           new NetworkManager.Builder()
            .showCustomSnack(this, getString(R.string.snk_network_connected), getString(R.string.snk_netword_disconnected), Color.WHITE, Color.BLACK, Color.RED, Color.GREEN) 
             .build();
  ```            
           
<b>To show alert dialog</b>
 ```
         new NetworkManager.Builder()
             .showAlert(this, this) // show default alert message
              .build();
  ```     
           
 <b>To show alert dialog with custom message and actions</b>
  ```
           new NetworkManager.Builder()
             .showCustomAlert(this, "Network has been disconnected", this) // show custom alert
             .build();
   ```
             
  
<b>To load activity with default message and action</b>
  ```       
        new NetworkManager.Builder()
              .loadPage() // load activity
              .build();
  ```
         
           
<b>To load activity with custom message and action</b>
 ```
           new NetworkManager.Builder()
              .loadCustomPage(Constants.networkMsg.DISCONNECTED_PAGE, false, true) //load activity with custom message
             .build();
 ```
 # Download

<b>Step 1. Add the JitPack repository to your build file</b>
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

<b>Step 2. Add the dependency</b>

	dependencies {
	        implementation 'com.github.Periyanayagam:NetworkPro:1.0'
	}

Kindly check in your device and if any issues, post in issue section. Will be fixed and updated.
