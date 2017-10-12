package com.example.clock;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    //1
    private List<Event> mEventList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        View eventView;
        TextView eventName;
        TextView eventDate;
        TextView eventTime;
        FloatingActionButton fab;

        public ViewHolder(View view) {
            super(view);

            eventView = view;
            eventName = (TextView) view.findViewById(com.example.clock.R.id.event_name);
            eventDate = (TextView) view.findViewById(com.example.clock.R.id.event_date);
            eventTime = (TextView) view.findViewById(com.example.clock.R.id.event_time);
            fab = view.findViewById(com.example.clock.R.id.fab);
        }

    }

    //3
    public EventAdapter(List<Event> eventList){
        this.mEventList=eventList;
    }

    //4
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.clock.R.layout.event_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);

        holder.eventView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Event event=mEventList.get(position);

                //设置提醒逻辑代码
                Toast.makeText(view.getContext(),"设置提醒",Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEventList.get(position);
        holder.eventName.setText(event.getName());
        holder.eventDate.setText(event.getDate());
        holder.eventTime.setText(event.getTime());
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }


/*    //删除数据
    public void removeData(int position){
        mEventList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mEventList.size() - position);
    }*/
}
