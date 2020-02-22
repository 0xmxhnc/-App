package com.example.efficientpomodoro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class WelcomeActivity extends Activity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        button = (Button)findViewById(R.id.enter) ;
        //创建子线程
        Thread myThread=new Thread(){
            @Override
            public void run() {
                try{
                    //使程序休眠两秒
                    sleep(2000);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //启动MainActivity
                            Intent intent = new Intent(WelcomeActivity.this ,MainActivity.class);
                            startActivity(intent);
                            //关闭当前活动
                            finish();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        //启动线程
        myThread.start();
    }
}
