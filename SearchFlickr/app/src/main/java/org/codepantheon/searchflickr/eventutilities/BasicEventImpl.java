package org.codepantheon.searchflickr.eventutilities;

import java.util.HashSet;
import java.util.Set;

public class BasicEventImpl {
    private final Event mEvent = new Event();

    public Event getEvent(){
        return mEvent;
    }

    public void invoke(){
        for(EventHandler eventHandler : mEvent.getEventHandlers()){
            if(eventHandler!=null) {
                eventHandler.invoke();
            }
        }
    }

    public interface EventHandler{
        void invoke();
    }

    public static class Event {
        private Set<EventHandler> mEventHandlers = new HashSet<>();

        public void add(EventHandler eventHandler){
            mEventHandlers.add(eventHandler);
        }

        public void remove(EventHandler eventHandler){
            mEventHandlers.remove(eventHandler);
        }

        private Set<EventHandler> getEventHandlers(){
            return mEventHandlers;
        }
    }
}
