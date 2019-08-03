package org.codepantheon.searchflickr.eventutilities;

import java.util.HashSet;
import java.util.Set;

public class BasicEvent {
    public interface EventHandler{
        void invoke();
    }

    private Set<EventHandler> mEventHandlers = new HashSet<>();

    public void add(EventHandler eventHandler){
        mEventHandlers.add(eventHandler);
    }

    public void remove(EventHandler eventHandler){
        mEventHandlers.remove(eventHandler);
    }

    public void invoke(){
        for(EventHandler eventHandler : mEventHandlers){
            if(eventHandler!=null) {
                eventHandler.invoke();
            }
        }
    }
}
