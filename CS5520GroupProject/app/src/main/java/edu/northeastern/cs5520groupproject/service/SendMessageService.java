package edu.northeastern.cs5520groupproject.service;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import edu.northeastern.cs5520groupproject.HomePageActivity;
import edu.northeastern.cs5520groupproject.MainActivity;
import edu.northeastern.cs5520groupproject.util.Utils;
import edu.northeastern.cs5520groupproject.R;

public class SendMessageService {

    private static final String TAG = "FCMActivity";
    private static String SERVER_KEY = "AAAAFjdA9JQ:APA91bHxX80bPoXph8x3DzYjZ1dcy6OBll4n_6l-iILES6rUq_PdoFaFsoB7BUFuPCLiLZTIpIETL-wG9Q7kOaNksHE7ZCYyRmYiOVT19EEQQXkIQAuv606jXlSUt3yoC66fPVYmE6xv";
    // This is the client registration token
    private static String CLIENT_REGISTRATION_TOKEN;

    public SendMessageService() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    //Toast.makeText(HomePageActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    if (CLIENT_REGISTRATION_TOKEN == null) {
                        CLIENT_REGISTRATION_TOKEN = task.getResult();
                    }
                    Log.e("CLIENT_REGISTRATION_TOKEN", CLIENT_REGISTRATION_TOKEN);
                    //Toast.makeText(HomePageActivity.this, "CLIENT_REGISTRATION_TOKEN Existed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendMessageToDevice(String targetToken, String fromUser, String toUser, int sticker) {

        // Prepare data
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jdata = new JSONObject();
        try {
            jNotification.put("title", "Hi " + toUser + ", " + fromUser + " has sent you a sticker");
            jNotification.put("body", "The sticker is " + sticker);
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            /*
            // We can add more details into the notification if we want.
            // We happen to be ignoring them for this demo.
            jNotification.put("click_action", "OPEN_ACTIVITY_1");
            */
            jdata.put("title", "data title from 'SEND MESSAGE TO CLIENT BUTTON'");
            jdata.put("content", "data content from 'SEND MESSAGE TO CLIENT BUTTON'");

            /***
             * The Notification object is now populated.
             * Next, build the Payload that we send to the server.
             */

            // If sending to a single client
            jPayload.put("to", targetToken); // CLIENT_REGISTRATION_TOKEN);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
            jPayload.put("data", jdata);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String resp = Utils.fcmHttpConnection(SERVER_KEY, jPayload);
        // Utils.postToastMessage("Status from Server: " + resp, );

    }
}
