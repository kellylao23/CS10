/**
 * Models one student at college
 * @author Kelly Lao
 */
public class Student12 {
    // instance variables
    String name;
    int year;

    public void setName(String name){
        this.name = name;
    }
    public void setYear(int gradYear){
        if (gradYear >= 1769 && gradYear <= 2028){
            year = gradYear;
        } else{
            System.out.println("Invalid year for " + name);
        }
    }

    public String getName(){return name;}
    public int getYear(){return year;}

    public static void main(String[] args) {
        Student12 alice = new Student12(); // alice is object of type Student12
        alice.setName("Alice");
        alice.setYear(2028);
        alice.setYear(-1);
        System.out.println(alice.name + " " + alice.year);

        Student12 abby = new Student12();
        abby.name = "Abby"; abby.year = 2027; //bad form
        System.out.println(abby.name + " " + abby.year);
    }
}
