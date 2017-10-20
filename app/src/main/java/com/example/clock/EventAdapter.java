package com.example.clock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

    class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private static final int SINGLE=0;
    private static final int DOUBLE=1;

    private List<Event> mEventList;
    private Context mContext;


/*    public class ViewHolder extends RecyclerView.ViewHolder{


        CardView cardView;
        TextView eventName;
        TextView eventTime;
        FloatingActionButton fab;


        public ViewHolder(View view) {
            super(view);

            cardView= (CardView) view;
            eventName = (TextView) view.findViewById(com.example.clock.R.id.event_name);
            eventTime = (TextView) view.findViewById(com.example.clock.R.id.event_time);
            cardView=(CardView) view.findViewById(R.id.cardview);
            fab = view.findViewById(com.example.clock.R.id.fab);



        }

    }*/

    //3
    public EventAdapter(List<Event> eventList,Activity context){
        this.mEventList=eventList;
        mContext=context;
    }

    //4
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    RecyclerView.ViewHolder holder = getViewHolderByViewType(viewType);
    return holder;

    }


    private RecyclerView.ViewHolder getViewHolderByViewType(int viewType){

        View view_single=View.inflate(mContext,R.layout.event_item_left, null);
        View view_double=View.inflate(mContext,R.layout.event_item_right, null);
        RecyclerView.ViewHolder holder =null;

        switch (viewType){
            case SINGLE:
                holder=new ViewHolder_Left(view_single);
                final RecyclerView.ViewHolder finalHolder1 = holder;
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //设置提醒逻辑代码
                        Toast.makeText(view.getContext(),"设置提醒",Toast.LENGTH_SHORT).show();

                        //跳转到编辑界面()
                        Intent intent=new Intent(mContext,SetRemindActivity.class);
                        ((ListActivity)mContext).startActivity(intent);

                    }
                });
                break;

            case DOUBLE:
                holder=new ViewHolder_Right(view_double);
                final RecyclerView.ViewHolder finalHolder = holder;
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        //设置提醒逻辑代码
                        Toast.makeText(view.getContext(),"设置提醒",Toast.LENGTH_SHORT).show();

                        //跳转到编辑界面()
                        Intent intent=new Intent(mContext,SetRemindActivity.class);
                        ((ListActivity)mContext).startActivity(intent);

                    }
                });
                break;

        }
        return holder;
    }


    class ViewHolder_Left extends RecyclerView.ViewHolder{
        TextView eventName;
        TextView eventTime;
        CardView cardView;
        FloatingActionButton fab;

        public ViewHolder_Left(View itemView){

            super(itemView);

            eventName = (TextView) itemView.findViewById(com.example.clock.R.id.left_item_content);
            eventTime = (TextView) itemView.findViewById(com.example.clock.R.id.left_tv_time);
            cardView=(CardView) itemView.findViewById(com.example.clock.R.id.cardview);
            fab = (FloatingActionButton) itemView.findViewById(com.example.clock.R.id.fab);
        }
    }


    class ViewHolder_Right extends RecyclerView.ViewHolder{

        TextView eventName;
        TextView eventTime;
        CardView cardView;
        FloatingActionButton fab;

        public ViewHolder_Right(View itemView){

            super(itemView);

            eventName = (TextView) itemView.findViewById(com.example.clock.R.id.right_item_content);
            eventTime = (TextView) itemView.findViewById(com.example.clock.R.id.right_tv_time);
            cardView=(CardView) itemView.findViewById(com.example.clock.R.id.cardview);
            fab = (FloatingActionButton) itemView.findViewById(com.example.clock.R.id.fab);
        }

    }


    //设置item的内容
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position%2==0) {

            Event event = mEventList.get(position);
            ((ViewHolder_Left) holder).eventName.setText(event.getName());
            ((ViewHolder_Left) holder).eventTime.setText(event.getTime());

        }else {

            Event event = mEventList.get(position);
            ((ViewHolder_Right)holder).eventName.setText(event.getName());
            ((ViewHolder_Right)holder).eventTime.setText(event.getTime());

        }

    }



    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return SINGLE;
        }else {
            return DOUBLE;
        }
    }
}



