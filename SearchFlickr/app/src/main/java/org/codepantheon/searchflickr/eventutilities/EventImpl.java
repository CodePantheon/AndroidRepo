package org.codepantheon.searchflickr.eventutilities;

import java.util.HashSet;
import java.util.Set;

public final class EventImpl<T> {
    private final Event<T> mEvent = new Event<>();

    public Event<T> getEvent(){
        return mEvent;
    }

    public void invoke(T param){
        for(EventHandler<T> eventHandler : mEvent.getEventHandlers()){
            if(eventHandler != null) {
                eventHandler.invoke(param);
            }
        }
    }

    public interface EventHandler<T>{
        void invoke(T param);
    }

    public static class Event<T> {
        private Set<EventHandler<T>> mEventHandlers = new HashSet<>();

        public void add(EventHandler<T> eventHandler){
            mEventHandlers.add(eventHandler);
        }

        public void remove(EventHandler<T> eventHandler){
            mEventHandlers.remove(eventHandler);
        }

        private Set<EventHandler<T>> getEventHandlers(){ return mEventHandlers; }
    }
}
