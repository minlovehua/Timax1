package com.example.clock;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class EventAdapterTest {

    //1
    private List<Event> mEventList;
    private Context mContext;

    @Test
    public void getCount() throws Exception {

    }

    @Test
    public void getItem() throws Exception {

    }

    @Test
    public void getView() throws Exception {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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

    }




    @Test
    public void onCreateViewHolder() throws Exception {

    }

    @Test
    public void onBindViewHolder() throws Exception {

    }

    @Test
    public void getItemCount() throws Exception {

    }

}