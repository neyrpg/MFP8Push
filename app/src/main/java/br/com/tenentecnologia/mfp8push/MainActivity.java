package br.com.tenentecnologia.mfp8push;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;
import com.worklight.wlclient.WLRequest;
import com.worklight.wlclient.api.WLAccessTokenListener;
import com.worklight.wlclient.api.WLAuthorizationManager;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;
import com.worklight.wlclient.api.challengehandler.BaseChallengeHandler;
import com.worklight.wlclient.api.challengehandler.SecurityCheckChallengeHandler;
import com.worklight.wlclient.auth.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "PUSH_MFP8";

    private WLClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);



        client = WLClient.createInstance(this);

        WLAuthorizationManager.getInstance().obtainAccessToken(null, new WLAccessTokenListener() {
            @Override
            public void onSuccess(AccessToken accessToken) {
                Log.i(TAG, "Token "+accessToken.getValue());
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                Log.i(TAG, wlFailResponse.getErrorMsg());
            }
        });

        WLClient.getInstance().registerChallengeHandler(new SecurityCheckChallengeHandler("UserLogin") {

            @Override
            public void handleChallenge(JSONObject jsonObject) {
                submitChallengeAnswer(jsonObject);
            }
        });
        MFPPush.getInstance().initialize(this);


        Boolean isSupported = MFPPush.getInstance().isPushSupported();

        if (isSupported ) {
            Log.i(TAG, "Com suporte");
        } else {
            Log.i(TAG, "Sem suporte");
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device", "sfd")
            ;
        } catch (JSONException e) {
            e.printStackTrace();
        }



        MFPPush.getInstance().registerDevice(jsonObject, new MFPPushResponseListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "s");
            }

            @Override
            public void onFailure(MFPPushException e) {
                Log.i(TAG, "erro");
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
            }
        });

        MFPPush.getInstance().listen(new MFPPushNotificationListener() {
            @Override
            public void onReceive(MFPSimplePushNotification mfpSimplePushNotification) {
                Log.i(TAG, mfpSimplePushNotification.getPayload());
                Log.i(TAG, mfpSimplePushNotification.getAlert());



            }
        });





//try {
//
//    WLResourceRequest request = new WLResourceRequest(new URI("/adapters/javaAdapter/resource"), WLResourceRequest.GET);
//    request.send(new WLResponseListener() {
//        @Override
//        public void onSuccess(WLResponse wlResponse) {
//            Log.i(TAG, wlResponse.getResponseText());
//        }
//
//        @Override
//        public void onFailure(WLFailResponse wlFailResponse) {
//            Log.e(TAG, wlFailResponse.getErrorMsg());
//        }
//    });
//}catch (Exception e){
//    Log.e(TAG, e.getMessage());
//}



    }
}
