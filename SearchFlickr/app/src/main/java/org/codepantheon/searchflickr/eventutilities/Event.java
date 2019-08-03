package org.codepantheon.searchflickr.eventutilities;

import java.util.HashSet;
import java.util.Set;

public final class Event<T> {
    public interface EventHandler<T>{
        void invoke(T param);
    }

    private Set<EventHandler<T>> mEventHandlers = new HashSet<>();

    public void add(EventHandler<T> eventHandler){
        mEventHandlers.add(eventHandler);
    }

    public void remove(EventHandler<T> eventHandler){
        mEventHandlers.remove(eventHandler);
    }

    public void invoke(T param){
        for(EventHandler<T> eventHandler : mEventHandlers){
            if(eventHandler != null) {
                eventHandler.invoke(param);
            }
        }
    }
}
