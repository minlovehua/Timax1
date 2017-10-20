package com.example.clock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SetRemindActivity extends Activity implements View.OnClickListener {

    private List<Event> eventList = new ArrayList<>();
    EventAdapter mAdapter;
    private int count;//记录eventList.size-1
    private int tiaoshu = 0;//测试：添加数据的条数


    private Button sure_btn;
    private Button cancel_btn;
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_remind);

        selectTime = (RelativeLayout) findViewById(R.id.selectTime);
        selectTime.setOnClickListener(this);
        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);


        //尝试添加新的提醒
        sure_btn=(Button) findViewById(R.id.sure_btn);
        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SetRemindActivity.this,"确定添加item啦",Toast.LENGTH_SHORT);
/*                count++;//记录添加的为位置
                tiaoshu++;//记录添加的是第几条数据
                eventList.add(1, new Event("第" + tiaoshu + "条添加", "2019.2.19  17:30"));//参数1：新添加数据的位置
                mAdapter.notifyItemInserted(1);//参数1：新添加数据的位置
                mAdapter.notifyItemRangeChanged(1, eventList.size() - 2);//参数1：新添加数据的位置*/
            }
        });


        cancel_btn=(Button) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(SetRemindActivity.this,"取消新提醒",Toast.LENGTH_SHORT);
            }
        });
        initDatePicker();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                break;

            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(currentTime.getText().toString());
                break;

            case R.id.sure_btn://添加提醒

        }
    }



    private void initDatePicker() {

        //SimpleDateFormat：java中用来对日期字符串进行解析和格式化输出的类，非线程安全
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

        //format()方法：格式输出
        String now = sdf.format(new Date());

        //split()方法：截取
        currentDate.setText(now.split(" ")[0]);
        currentTime.setText(now);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2017-08-31 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动



        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentTime.setText(time);
            }
        }, "2017-08-31 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

}

