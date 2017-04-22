package lab3.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BoundService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private static int counter;
    private Toast toast;
    private boolean run;

    public class LocalBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }

    public BoundService() {
        this.counter = 0;
        this.run = true;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);

    }



    @Override
    public IBinder onBind(Intent intent) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute(){
                try{
                    Thread.sleep(10000);
                    showToast("Your bound service has been started.");
                } catch (InterruptedException e) {

                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    while(run){
                        Thread.sleep(4000);
                        counter++;
                        publishProgress(params);
                    }
                } catch (Exception exception) {

                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values){
                super.onProgressUpdate(values);
                showToast("Your bound service is still working");
            }

            @Override
            protected void onPostExecute(Void result){
                showToast("Your bound service has been stopped");
            }
        }.execute();

        return mBinder;
    }

    public void showToast(String text){
        toast.setText(text);
        toast.show();
    }

    public int getCounter(){
        return this.counter;
    }

    public void stopRuning(){
        this.run = false;
    }
}
