package com.example.efficientpomodoro;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//使用第三方图片加载库
import com.bumptech.glide.Glide;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    public static final String Clock_Name = "clock_name";
    public static final String Clock_Image_Id = "clock_image_id";
    private EditText getcountdown;
    private TextView show;

    Integer integer = null;
    Button begin_countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //获取传入的时钟名和背景图片资源id
        Intent intent = getIntent();
        String clockName = intent.getStringExtra(Clock_Name);
        int clockImageId = intent.getIntExtra(Clock_Image_Id, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView clockImageView = (ImageView) findViewById(R.id.background_image_view);
        TextView clockContentText = (TextView) findViewById(R.id.content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(clockName);
        Glide.with(this).load(clockImageId).into(clockImageView);
        String clockContent = generateClockContent(clockName);
        clockContentText.setTextSize(24);
        clockContentText.setText(clockContent);

        //点击按钮执行倒计时模块
        begin_countdown = (Button)findViewById(R.id.set_begin);
        begin_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcountdown = (EditText)findViewById(R.id.edit_time);
                long num = (long) integer.parseInt(getcountdown.getText().toString());
                //long num = (long) integer.parseInt(num1);
                show = (TextView)findViewById(R.id.show_countdown);

                //使用倒计时类,传入倒计时数值
                CountDownTimer timer = new CountDownTimer(num * 60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        show.setText(FormatTime(millisUntilFinished));
                        show.setTextSize(24);
                    }

                    @Override
                    public void onFinish() {
                        //倒计时结束时弹框
                        //提醒用户完成了学习
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this)
                                .setTitle("该番茄钟已完成").setIcon(R.drawable.tomato2).setMessage("请继续加油！");

                        //倒计时结束同时震动
                        VibrateHelper vibrateHelper = new VibrateHelper();
                        //开启震动
                        vibrateHelper.Vibrate(SettingsActivity.this, 1000);
                        //关闭震动
                        vibrateHelper.VibrateCancle(SettingsActivity.this);

                        //添加确定按钮
                        setPositiveButton(builder).create().show();
                        //保留取消按钮相关代码,以备不时之需
                        //添加取消按钮
                        //setNegativeButton(builder).create().show();
                        show.setText("倒计时完毕");
                    }

                    //将毫秒转换成"分钟:秒"的格式
                    public String FormatTime(long millisUntilFinished){
                        //分钟变量
                        long minute;
                        //秒数变量
                        long second;
                        minute = ((millisUntilFinished / 1000) / 60);
                        second = ((millisUntilFinished / 1000) % 60);
                        if (minute < 10) {
                            if (second < 10) {
                                return "0" + minute + ":" + "0" + second;
                            } else {
                                return "0" + minute + ":" + second;
                            }
                        }else {
                            if (second < 10) {
                                return minute + ":" + "0" + second;
                            } else {
                                return minute + ":" + second;
                            }
                        }
                    }
                };
                //倒计时功能生效
                timer.start();
            }
        });
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        //为setPositiveButton方法添加确定按钮
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SettingsActivity.this, "Keep Studying", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
//        //为setNegativeButton方法添加取消按钮
//        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(SettingsActivity.this, "Keep Studying", Toast.LENGTH_SHORT).show();
//                //show.setText("单击了取消按钮");
//            }
//        });
//    }

    private String generateClockContent(String clock_Name) {
        //将番茄钟编号传入设置详情页
        StringBuilder clockContent = new StringBuilder();
        clockContent.append(clock_Name);
        return clockContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
