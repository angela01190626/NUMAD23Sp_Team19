package Notification;

import android.database.Observable;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization: key = AAAAKDZh-M4:APA91bHkBfQWw5bAdDkZfLoP439osIKSZojG0ftQWX_xGo-2MIxky47YBX4xK5-fXWQtkYvPCBWCCximbkFUIR4wjNmJj2b3Nvc6rm5uWbXCBy9lgP01D7qg27BEtYIglgBwSx3ZOUbS"
    })

    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);

}
