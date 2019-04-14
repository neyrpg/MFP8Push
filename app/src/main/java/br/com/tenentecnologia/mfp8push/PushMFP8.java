package br.com.tenentecnologia.mfp8push;

import android.util.Log;

import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

public class PushMFP8 extends MFPPush implements MFPPushNotificationListener {
    private static String TAG = "PushMFP8";
    @Override
    public void onReceive(MFPSimplePushNotification mfpSimplePushNotification) {
        Log.i(TAG, mfpSimplePushNotification.getPayload());
        Log.i(TAG, mfpSimplePushNotification.getAlert());
    }

    public PushMFP8() {
        super();
        super.listen(this);
    }
}
