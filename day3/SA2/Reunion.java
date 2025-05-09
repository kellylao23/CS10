package SA2;

/**
 * Short Assignment 2 (SA-2)
 * Model attendees and graduates at a reunion.
 *
 * @author Kelly Lao, Dartmouth CS10, Winter 2025
 */
public class Reunion{
    Attendee[] list;
    int maxAttendees;
    int currentAttendees;

    /**
     *
     * @param max sets maximum number of attendees
     */
    public Reunion(int max){
        maxAttendees = max;
        list = new Attendee[max];
    }

    /**
     * Adds new attendee object to list of current attendees
     * @param attendee
     */
    public void addAttendee(Attendee attendee){
        if (currentAttendees >= maxAttendees){
            System.out.println("Unable to accommodate " + attendee + ", max attendees is " + maxAttendees);
        }else{
            list[currentAttendees] = attendee;
            System.out.println("Added " + attendee + ", rsvp: " + attendee.getRsvp());
            currentAttendees += 1;
        }
    }

    /**
     * Changes and prints the RSVP response of attendee
     * @param name of attendee
     * @param rsvp status of attendee
     */
    public void rsvp(String name, boolean rsvp){
        boolean found = false;
        for (Attendee a : list){
            if (name.equals(a.getName())) {
                a.setRsvp(rsvp);
                System.out.println("Set RSVP to " + rsvp + " for " + name);
                found = true;
            }
        }
        if (found == false){
            System.out.println(name + " not found");
        }
    }

    /**
     * @return string specified by given format
     */
    public String toString(){
        String s = "Reunion attendees that have RSVP: \n";
        for (Attendee a : list){
            if (a.getRsvp()){
                s += "\t" + a + "\n";
            }
        }
        s += "\nReunion attendees that have NOT RSVP: \n";
        for (Attendee a : list){
            if (!a.getRsvp()){
                s += "\t" + a + "\n";
            }
        }
        return s;
    }

    public static void main(String[] args) {
        Reunion r = new Reunion(5);
        r.addAttendee(new Attendee("Alice", true));
        r.addAttendee(new Attendee("Bob", false));
        r.addAttendee(new Graduate("Charlie", true, 22, "Government"));
        r.addAttendee(new Graduate("Denise", false, 21, "Econ"));
        r.addAttendee(new Graduate("Elvis", true, 23, "Computer Science"));
        r.addAttendee(new Graduate("Falcon", false, 26, "Biology"));
        System.out.println(r);
        System.out.println();

        System.out.println("Update rsvp status");
        r.rsvp("George", false);  //print not found
        r.rsvp("Bob", true);  //update
        System.out.println(r);
    }
}
