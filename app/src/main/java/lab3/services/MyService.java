package lab3.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private Timer timer;
    private TimerTask timerTask;
    private Toast toast;

    public MyService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        timer = new Timer();
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        clearTimer();
        initTask();
        timer.scheduleAtFixedRate(timerTask,5000,5000);
        showToast("Service has been started.");
        Log.d("Service","onStartCommand");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        clearTimer();
        showToast("Your service has been stopped");
        super.onDestroy();
    }

    public void showToast(String text){
        toast.setText(text);
        toast.show();
    }

    private void initTask(){
        timerTask = new MyTimerTask();
    }

    private void clearTimer(){
        if(timerTask != null){
            timerTask.cancel();
            timer.purge();
        }
    }

    private class MyTimerTask extends TimerTask{
        @Override
        public void run(){
            Log.d("Service","TimerTask");
            showToast("Your service is still working.");
        }
    }
}
