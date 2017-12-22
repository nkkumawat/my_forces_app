package me.nkkumawat.mycodeforces.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import me.nkkumawat.mycodeforces.MainActivity;
import me.nkkumawat.mycodeforces.R;


/**
 * Created by sonu on 21/12/17.
 */

public class NotificationService extends Service {
    SharedPreferences sharedpreferences;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }
    @Override
    public void onStart(Intent intent, int startId) {
        String Response = sharedpreferences.getString("contestDetails" , "[]");
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(Response);
            JSONObject json_data = jArray.getJSONObject(0);
            String name = json_data.getString("contestName").replace("\n" , "");
            String date  =  json_data.getString("startDate").replace("\n" , "").replace(" " , "");
            String code  =  json_data.getString("contestCode").replace("\n" , "").replace(" " , "");
            addNotification(name , date + " " + code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    private void addNotification(String title , String message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher_background).setContentTitle(title).setContentText(message);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
//        Uri ringNotify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Ringtone ringtone = RingtoneManager.getRingtone(this, ringNotify);
//        ringtone.play();
    }
}
