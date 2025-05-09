package SA2;

public class Attendee {
    String name;
    boolean rsvp;

    /**
     * Constructor - defines an attendee
     * @param name - name of attendee
     * @param rsvp - whether the attendee has rsvp'd
     */
    public Attendee(String name, boolean rsvp){
        this.name = name;
        this.rsvp = rsvp;
    }

    /**
     * Getter Methods
     */
    public String getName(){return this.name;}
    public boolean getRsvp(){return this.rsvp;}

    /**
     * Setter Methods
     */
    public void setName(String name){this.name = name;}
    public void setRsvp(boolean rsvp){this.rsvp = rsvp;}

    @Override
    public String toString(){
        String s = name;
        return s;
    }
}
