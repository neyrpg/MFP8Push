package br.com.tenentecnologia.mfp8push;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushIntentService;
import com.worklight.common.WLConfig;

import org.json.JSONObject;

import java.util.Map;

public class PushIntentServiceMPF8 extends MFPPushIntentService {
    private static String TAG = "PushIntentServiceMPF8";

    public PushIntentServiceMPF8() {
        WLConfig.createInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> data = message.getData();
        JSONObject obj = new JSONObject(data);
        Log.i(TAG, obj.toString());
        Log.i(TAG, "Push Sucesso");

        super.onMessageReceived(message);
    }
}
