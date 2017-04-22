package lab3.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BoundService mService;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Activity","onCreate");
        Context context = getApplicationContext();
        Intent serviceIntent = new Intent(context,MyService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Thread t = new Thread(){
            public void run(){
                getApplicationContext().bindService(
                        new Intent(getApplicationContext(), BoundService.class), mConnection,
                        Context.BIND_AUTO_CREATE
                );
            }
        };
        t.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.LocalBinder binder  = (BoundService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    public void showCounter(View view) {
        if(mBound){
            int counter = mService.getCounter();
            Toast.makeText(this, Integer.toString(counter), Toast.LENGTH_SHORT).show();
        }
    }

    public void startListActivity(View view) {
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }
}
