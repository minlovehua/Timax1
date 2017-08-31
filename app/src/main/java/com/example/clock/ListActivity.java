package com.example.clock;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.widget.Toast.makeText;


public class ListActivity extends AppCompatActivity implements EventAdapter.IonLeftSlideViewClickListener{


    private static final String TAG = "ListActivity";
    Toolbar toolbar;
    RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private Event[] events = {new Event("唱K", "2019.5.25", "17:30"), new Event("逛街", "2018.6.21", "14:30")};
    private List<Event> eventList = new ArrayList<>();
    private EventAdapter mAdapter;
    private int count;//记录eventList.size-1
    private int tiaoshu=0;//测试：添加数据的条数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(com.example.clock.R.id.recycler_view);

        //设置ToolBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Timax");


        //悬浮按钮.....................................................................................................................
        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"添加前,eventList.size()="+count,Toast.LENGTH_LONG).show();
                count++;//记录添加的为位置
                tiaoshu++;//记录添加的是第几条数据
                eventList.add(0,new Event("第"+tiaoshu+"条添加","2019.5.25", "17:30"));//参数1：新添加数据的位置
                mAdapter.notifyItemInserted(0);//参数1：新添加数据的位置
                mAdapter.notifyItemRangeChanged(0,eventList.size()-2);//参数1：新添加数据的位置

            }
        });
        //..............................................................................................................................






        //设置侧滑菜单效果
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_action_list);
        }


        //设置导航View
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.remind);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


        //设置适配器
        setAdapter();

        //初始化item
        initEvents();
    }


    //设置适配器
    private void setAdapter(){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new EventAdapter(eventList,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置控制Item增删的动画


        //gridLayoutManager.scrollToPositionWithOffset(position,0);
        //gridLayoutManager.setStackFromEnd(true);
    }


    //初始化提醒事件
    private void initEvents() {
        eventList.clear();
        for (int i = 0; i < 10; i++) {
            count=i;
            Random random = new Random();
            int index = random.nextInt(events.length);
            eventList.add(events[index]);
        }
    }


    /**
     * item(提醒)的点击事件
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG,"点击项："+position);
    }


    /**
     * item的左滑设置
     * @param view
     * @param position
     */
    @Override
    public void onSetBtnCilck(View view, int position) {
        makeText(ListActivity.this,"请设置",Toast.LENGTH_LONG).show();
    }


    /**
     * item的左滑删除
     * @param view
     * @param position
     */
    @Override
    public void onDeleteBtnCilck(View view, int position) {
        Log.i(TAG,"删除项："+position);
        mAdapter.removeData(position);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.setting:
                makeText(this, "设置", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }


}
