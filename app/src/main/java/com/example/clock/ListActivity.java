package com.example.clock;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static android.widget.Toast.makeText;


public class ListActivity extends AppCompatActivity {


    private List<Event> eventList = new ArrayList<>();

    private static final String TAG = "ListActivity";
    Toolbar toolbar;
    RecyclerView mRecyclerView;
    EventAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private Event[] events = {new Event("唱K", "2019.7.25", "17:30"), new Event("逛街", "2018.6.21", "14:30")};
    private int count;//记录eventList.size-1
    private int tiaoshu = 0;//测试：添加数据的条数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 将数据按照时间排序
        DateComparator comparator = new DateComparator();
        Collections.sort(eventList, comparator);

        mRecyclerView = (RecyclerView) findViewById(com.example.clock.R.id.recycler_view);

        //设置ToolBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Timax");


        //悬浮按钮.....................................................................................................................
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count++;//记录添加的为位置
                tiaoshu++;//记录添加的是第几条数据
                eventList.add(1, new Event("第" + tiaoshu + "条添加", "2019.5.25", "17:30"));//参数1：新添加数据的位置
                mAdapter.notifyItemInserted(1);//参数1：新添加数据的位置
                mAdapter.notifyItemRangeChanged(1, eventList.size() - 2);//参数1：新添加数据的位置

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


        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            private RecyclerView.ViewHolder vh;

            //Item是否可以滑动
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            //Item是否可以长按
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder) {
                // 拖拽的标记，这里允许上下左右四个方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                // 滑动的标记，这里允许左右滑动
                int swipeFlags = ItemTouchHelper.START;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /*
                这个方法会在某个Item被拖动和移动的时候回调，这里我们用来播放动画，当viewHolder不为空时为选中状态
                否则为释放状态
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    vh = viewHolder;
                    pickUpAnimation(viewHolder.itemView);
                } else {
                    if (vh != null) {
                        putDownAnimation(vh.itemView);
                    }
                }
            }


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // 移动时更改列表中对应的位置并返回true
                Collections.swap(eventList, viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
                return true;
            }

            /*
                当onMove返回true时调用
             */
            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int
                    fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                // 移动完成后刷新列表
                mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {


                AlertDialog show = new AlertDialog.Builder(ListActivity.this)
                        .setTitle(" ")                                                   //设置对话框标题
                        .setMessage("确定删除此数据？")                                    //设置显示的内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() { //添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                                // 将数据集中的数据移除
                                eventList.remove(viewHolder.getAdapterPosition());
                                // 刷新列表
                                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                //mAdapter.removeData(position);

                                //Toast.makeText(EventAdapter1.this,"删除成功！",Toast.LENGTH_LONG).show();
                                //finish();//关闭应用
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Toast.makeText(EventAdapter.this,"恢复，不删除！",Toast.LENGTH_LONG).show();
                            }
                        }).show();

            }
        }).attachToRecyclerView(mRecyclerView);


        //设置适配器
        setAdapter();


        //初始化item
        initEvents();

    }


    //设置适配器
    private void setAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new EventAdapter(eventList);
        mRecyclerView.setAdapter(mAdapter);
    }


    //初始化提醒事件
    private void initEvents() {
        eventList.clear();
        for (int i = 0; i < 10; i++) {
            count = i;
            Random random = new Random();
            int index = random.nextInt(events.length);
            eventList.add(events[index]);
        }
    }


/*    @Override
    public void onDeleteBtnCilck(View view, final int position) {

        new AlertDialog.Builder(ListActivity.this)
                .setTitle(" ")                                                   //设置对话框标题
                .setMessage("确定删除此数据？")                                    //设置显示的内容
                .setPositiveButton("确定",new DialogInterface.OnClickListener() { //添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mAdapter.removeData(position);

                        //Toast.makeText(EventAdapter1.this,"删除成功！",Toast.LENGTH_LONG).show();
                        //finish();//关闭应用
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(EventAdapter1.this,"返回成功！",Toast.LENGTH_LONG).show();
            }
        }).show();
          } */


/*        Snackbar.make(view,"Data delete", Snackbar.LENGTH_SHORT).setAction("Undo",new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(ListActivity.this,"Data restored",Toast.LENGTH_LONG).show();
            }
        }).show();*/


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


    //浮起的动画
    private void pickUpAnimation(View view) {
        //第一个参数为 view对象，第二个参数为 动画改变的类型，第三，第四个参数依次是开始透明度和结束透明度。
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 3f, 10f);
        //设置动画插入器，减速
        animator.setInterpolator(new DecelerateInterpolator());
        //设置动画时间
        animator.setDuration(300);
        //启动动画。
        animator.start();
    }

    //下沉的动画
    private void putDownAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 3f, 1f);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }

}
