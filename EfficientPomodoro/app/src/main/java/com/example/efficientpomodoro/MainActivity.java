package com.example.efficientpomodoro;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    //新建番茄钟的集合
    private List<Pomodoro> pomodoroList = new ArrayList<>();
    //自定义适配器
    private PomodoroAdapter adapter;
    //刷新视图
    private SwipeRefreshLayout swipeRefresh;

    //随机番茄钟的背景图
    private int[] imageIds = new int[]
            {R.drawable.beautiful_tree, R.drawable.blossom_flower, R.drawable.butterfly_brown,
            R.drawable.cereals, R.drawable.flower_lily, R.drawable.flower_sunshine,
            R.drawable.rain_flower, R.drawable.red_blossom, R.drawable.tree_lake,
            R.drawable.tree_sky};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加新的番茄钟
                addnewClock();
                adapter.notifyDataSetChanged();
                //Snackbar允许在提示中加入一个可交互按钮
                Snackbar.make(view, "番茄钟已添加", Snackbar.LENGTH_LONG)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "撤销新建番茄钟成功", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //初始化番茄钟
        initFruits();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PomodoroAdapter(pomodoroList);
        recyclerView.setAdapter(adapter);

        //处理具体刷新逻辑
        //绑定id,获取SwipeRefreshLayout示例
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //设置下拉进度条颜色
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        //定义下拉刷新监听器
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            //处理具体的刷新操作
            //通过情况下,下拉刷新是去获取网络上最新的资源
            //我们这儿去执行一个本地刷新的方法
            public void onRefresh() {
                refreshPomodoro();
            }
        });
    }

    //刷新任务交给子线程完成
    private void refreshPomodoro() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //睡眠,以便看到刷新的过程
                    Thread.sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将线程切回到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //通知适配器数据发生了变化
                        adapter.notifyDataSetChanged();
                        //表示刷新事件结束,并隐藏刷新进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.task) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //单击侧边栏选项的响应动作
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(MainActivity.this, "点击其他地方试试呢", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(MainActivity.this, "点击其他地方试试呢", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(MainActivity.this, "点击其他地方试试呢", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(MainActivity.this, "点击其他地方试试呢", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this, "点击其他地方试试呢", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this, "点击其他地方试试呢", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFruits() {
        pomodoroList.clear();
        //初始化6个番茄钟
        Pomodoro p1 = new Pomodoro("Clock1", R.drawable.beautiful_tree);
        Pomodoro p2 = new Pomodoro("Clock2", R.drawable.blossom_flower);
        Pomodoro p3 = new Pomodoro("Clock3", R.drawable.butterfly_brown);
        Pomodoro p4 = new Pomodoro("Clock4", R.drawable.cereals);
        Pomodoro p5 = new Pomodoro("Clock5", R.drawable.flower_lily);
        Pomodoro p6 = new Pomodoro("Clock6", R.drawable.flower_sunshine);
        //Pomodoro p7 = new Pomodoro("Clock7", R.drawable.rain_flower);
        //将番茄钟添加到集合
        pomodoroList.add(p1);
        pomodoroList.add(p2);
        pomodoroList.add(p3);
        pomodoroList.add(p4);
        pomodoroList.add(p5);
        pomodoroList.add(p6);
        //pomodoroList.add(p7);
    }

    //在子线程中增加新的番茄钟
    public void addnewClock() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //pomodoroList.clear();
                Random random = new Random();
                int index = random.nextInt(imageIds.length);
                Pomodoro p8 = new Pomodoro("New Clock", imageIds[index]);
                pomodoroList.add(p8);
            }
        }).start();
    }
}
