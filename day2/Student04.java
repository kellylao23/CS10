public class Student04 {
    protected String name;
    protected int graduationYear;

    public Student04(){

    }

    public Student04(String name, int year){
        this.name = name;
        graduationYear = year;
    }

    public String getName(){return name;}
    public int getGraduationYear(){return graduationYear;}

    public static void main(String[] args) {
        Student04 kelly = new Student04();
        Student04 daniel = new Student04("Daniel", 2031);
        System.out.println("Name: " + kelly.name + ", Year: " + kelly.graduationYear);
        System.out.println("Name: " + daniel.name + ", Year: " + daniel.graduationYear);
    }

}
