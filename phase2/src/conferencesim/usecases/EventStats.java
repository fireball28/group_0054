package conferencesim.usecases;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import conferencesim.entities.eventpack.Event;

public class EventStats extends EventManager implements Serializable {


    List<Event> eventList;

    public EventStats(List<Event> eventList){
        this.eventList = eventList;
    }

    /**
     * Gets the most popular event
     * @return eventID of the event with currently the most attendees signed up.
     */
    public void mostAttended() {

        int j = -1;
        String eventId = "No events yet";

        for(Event e: this.eventList){
            if(j > e.getAttendees().size()){
                continue;
            }

            j = e.getAttendees().size();
            eventId = e.getEventID();
        }

        view.callView().print(eventId);
    }

//    /**
//     * Gets the eventID of the event that is the longest
//     * @return eventID of the longest duration
//     */
//
//    public String longestEvent() {
//
//        Duration j;
//        Duration k = null;
//        String eventId = "No events yet";
//
//        for(Event e: this.eventList){
//
//            if(k.equals(null)){
//                k = e.getDuration();
//                eventId = e.getEventID();
//                continue;
//            }
//
//            j = e.getDuration();
//
//            if(j.compareTo(k) > 0){
//                eventId = e.getEventID();
//                k = e.getDuration();
//            }
//        }
//
//        view.callView().print(eventId);
//        return eventId;
//    }


//    /**
//     * Gets the eventID of the first event in the given day
//     * @param day the day to inquire about
//     * @return eventID of the earliest event
//     */
//
//    public String earliestEvent(LocalDateTime day){
//
//        LocalDateTime j;
//        LocalDateTime k = null;
//        String eventId = "No events yet";
//
//        for(Event e: this.eventList){
//
//            if(0 == day.getDayOfMonth() - e.getStartTime().getDayOfMonth()){
//
//                if(k.equals(null)){
//                    k = e.getStartTime();
//                    eventId = e.getEventID();
//                    continue;
//                }
//
//                j = e.getStartTime();
//
//                if(j.isBefore(k)){
//                    eventId = e.getEventID();
//                    k = e.getStartTime();
//                }
//
//            }
//        }
//
//        view.callView().print(eventId);
//        return eventId;
//    }

    /**
     * Gets the eventID of the with the most speakers
     * @return eventID of event with most speakers
     */

    public String eventWithMostSpeakers() {

        int i = -1;
        String event = "No events yet";

        for(Event e: this.eventList){
            if(i > e.getSpeakers().size()){
                continue;
            }

            i = e.getSpeakers().size();
            event = e.getEventID();
        }

        view.callView().print(event);
        return event;
    }

    /**
     * Gets the eventID of the most attended event and removes it from the event list
     * @return eventID of event with most attended
     */

    public String topFiveAttendedHelper() {

        int j = -1;
        String rankOne = "No events yet";

        List<Event> lst = new ArrayList<>();
        lst =  this.eventList;

        for(Event e: lst){
            if(j > e.getAttendees().size()){
                continue;
            }

            j = e.getAttendees().size();
            rankOne = e.getEventID();
            lst.remove(rankOne);

        }

        return rankOne;
    }

    /**
     * Returns a list of the top 5 most attended events
     * @return list of eventIDs of the 5 most attended events
     */

    public List<String> topFiveAttended() {
        String rankOne = topFiveAttendedHelper();
        String rankTwo = topFiveAttendedHelper();
        String rankThree =  topFiveAttendedHelper();
        String rankFour = topFiveAttendedHelper();
        String rankFive = topFiveAttendedHelper();

        List<String> topFive = new ArrayList<String>();
        topFive.add(rankOne);
        topFive.add(rankTwo);
        topFive.add(rankThree);
        topFive.add(rankFour);
        topFive.add(rankFive);

        for(String rank:topFive){
            view.callView().print(rank);
        }
        return topFive;
    }
}
