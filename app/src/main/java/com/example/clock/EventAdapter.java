package com.example.clock;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;



public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements LeftSlideView.IonSlidingButtonListener{


    private Context mContext;
    private static List<Event> mEventList;

    private IonLeftSlideViewClickListener mIDeleteBtnClickListener;
    private IonLeftSlideViewClickListener mISetBtnClickListener;
    private LeftSlideView mMenu=null;

        //构造方法
        public EventAdapter(List<Event> eventList,Context context) {
        mEventList = eventList;

        mContext = context;
        mIDeleteBtnClickListener = (IonLeftSlideViewClickListener) context;
        mISetBtnClickListener=(IonLeftSlideViewClickListener) context;


    }


    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Event event = mEventList.get(position);
        holder.eventName.setText(event.getName());
        holder.eventDate.setText(event.getDate());
        holder.eventTime.setText(event.getTime());

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);



        holder.eventName.setOnClickListener(new View.OnClickListener() {//?????????item的空白处怎么表示？？？(这里应该为item的内容空白处包括正文内容)
            @Override
            public void onClick(View v) {

                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }
        });




        //左滑设置监听
        holder.btn_Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = holder.getLayoutPosition();
                mISetBtnClickListener.onSetBtnCilck(view,n);
            }
        });


        //左滑删除监听
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(view, n);
            }
        });


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(com.example.clock.R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }


    //自定义ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView eventName;
        TextView eventDate;
        TextView eventTime;
        public TextView btn_Set;
        public TextView btn_Delete;
        public ViewGroup layout_content;
        public FloatingActionButton fab;


        public ViewHolder(View itemview) {
            super(itemview);

            eventName = (TextView) itemview.findViewById(com.example.clock.R.id.event_name);
            eventDate = (TextView) itemview.findViewById(com.example.clock.R.id.event_date);
            eventTime = (TextView) itemview.findViewById(com.example.clock.R.id.event_time);


            btn_Set=(TextView) itemView.findViewById(com.example.clock.R.id.tv_set);
            btn_Delete = (TextView) itemView.findViewById(com.example.clock.R.id.tv_delete);
            layout_content = (ViewGroup) itemView.findViewById(com.example.clock.R.id.layout_content);

            fab=itemview.findViewById(com.example.clock.R.id.fab);

            ((LeftSlideView) itemView).setSlidingButtonListener(EventAdapter.this);
        }
    }




    //删除数据
    public void removeData(int position){
        mEventList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mEventList.size() - position);

    }


    //删除菜单打开信息接收
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }


    //滑动或者点击了Item监听
    @Override
    public void onDownOrMove(LeftSlideView lelfSlideView) {
        if(menuIsOpen()){
            if(mMenu != lelfSlideView){
                closeMenu();
            }
        }
    }

    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        Log.i("asd","mMenuΪnull");
        return false;
    }



    public interface IonLeftSlideViewClickListener {
        void onItemClick(View view, int position);
        void onDeleteBtnCilck(View view,int position);
        void onSetBtnCilck(View view,int position);
    }

}
