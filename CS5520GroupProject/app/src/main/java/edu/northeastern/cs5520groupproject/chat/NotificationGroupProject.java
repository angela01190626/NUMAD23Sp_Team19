package edu.northeastern.cs5520groupproject.chat;

import com.onesignal.OneSignal;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationGroupProject {

    public NotificationGroupProject(String message, String heading, String notificationKey, String extraField, String extraData) {
        System.out.println(message);
        System.out.println(notificationKey);
        if (extraField != null && extraData != null) {
            try {
                JSONObject notificationContent = new JSONObject(
                        "{'contents':{'en':'" + message + "','Content':'" + message + "'}," +
                                "'include_player_ids':['" + notificationKey + "']," +
                                "'headings':{'en': '" + heading + "'}," + "'data':{'" + extraField + "':'" + extraData + "'}}");
                OneSignal.postNotification(notificationContent, null);
            } catch (JSONException e) {
                Log.d("error", e.toString());
                e.printStackTrace();
            }
        } else {
            try {
                JSONObject notificationContent = new JSONObject(
                        "{'contents':{'en':'" + message + "','Content':'" + message + "'}," +
                                "'include_player_ids':['" + notificationKey + "']," +
                                "'headings':{'en': '" + heading + "'}}");
                OneSignal.postNotification(notificationContent, null);
            } catch (JSONException e) {
                Log.d("error", e.toString());
                e.printStackTrace();
            }
        }
    }
}
