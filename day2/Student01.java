public class Student01 {
    protected String name;
    protected int graduationYear;

    public void setName(String name){this.name = name;}
    public void setGraduationYear(int year){graduationYear = year;}

    public static void main(String[] args) {
        Student01 kelly = new Student01();
        kelly.setName("Kelly");
        kelly.setGraduationYear(2028);
        System.out.println("Name: " + kelly.name + ", Year: " + kelly.graduationYear);
    }
}