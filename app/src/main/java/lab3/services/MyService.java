package lab3.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

    private Toast toast;

    public MyService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute(){
                try{
                    Thread.sleep(5000);
                    showToast("Service has been started.");
                } catch (InterruptedException e) {

                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    while(true){
                        Thread.sleep(5000);
                        publishProgress(params);
                    }


                } catch (Exception exception) {

                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values){
                super.onProgressUpdate(values);
                showToast("Your service is still working.");
            }

            @Override
            protected void onPostExecute(Void result){
                showToast("Your service has been stopped");
            }
        }.execute();

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
        super.onDestroy();
    }

    public void showToast(String text){
        toast.setText(text);
        toast.show();
    }
}
