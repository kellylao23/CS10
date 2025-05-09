package SA2;

public class Graduate extends Attendee{
    int graduationYear;
    String department;

    public Graduate(String name, boolean rsvp, int graduationYear, String department){
        super(name, rsvp);
        this.graduationYear = graduationYear;
        this.department = department;
    }

    @Override
    public String toString(){
        String s = super.toString() + " '" + graduationYear + " " + department;
        return s;
    }
}
