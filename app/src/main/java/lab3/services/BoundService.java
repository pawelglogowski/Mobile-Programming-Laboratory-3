package lab3.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BoundService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private static int counter;
    private Timer timer;
    private TimerTask timerTask;
    private Toast toast;
    MainActivity activity;

    public class LocalBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }

    public BoundService() {
        this.counter = 0;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        timer = new Timer();
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        showToast("Your bound service has been started.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        clearTimer();
        initTask();
        timer.scheduleAtFixedRate(timerTask,5000,3000);
        return mBinder;
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

    public void showToast(String text){
        toast.setText(text);
        toast.show();
    }

    private class MyTimerTask extends TimerTask{
        @Override
        public void run(){
           new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {

                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                showToast("Your bound service is still working");
                            }
                        });
                    } catch (Exception exception) {

                    }
                    return null;
                }
            }.execute();
            counter++;
        }
    }

    public int getCounter(){
        return this.counter;
    }
}
