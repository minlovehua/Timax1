package com.example.clock;

import java.util.Comparator;

//比较提醒事件的时间先后
public class DateComparator implements Comparator<Event> {
    @Override
    public int compare(Event lhs, Event rhs) {
        return rhs.getDate().compareTo(lhs.getDate());
    }
}
