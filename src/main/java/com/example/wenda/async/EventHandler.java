package com.example.wenda.async;

import java.util.List;

/**
 * Created by chen on 2018/12/4.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
