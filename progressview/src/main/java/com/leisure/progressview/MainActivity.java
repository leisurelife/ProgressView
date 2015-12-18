package com.leisure.progressview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MyProgress mProgressLeft,mProgressTop,mProgressRight,mProgressBottom;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgressLeft.setProgress(msg.what);
            mProgressTop.setProgress(msg.what);
            mProgressRight.setProgress(msg.what);
            mProgressBottom.setProgress(msg.what);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressLeft= (MyProgress) findViewById(R.id.left_progress);
        mProgressTop= (MyProgress) findViewById(R.id.top_progress);
        mProgressRight= (MyProgress) findViewById(R.id.right_progress);
        mProgressBottom= (MyProgress) findViewById(R.id.bottom_progress);
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i=1;i<=100;i++){
                    mHandler.sendEmptyMessage(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
