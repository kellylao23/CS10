public class Student02 {
    protected String name;
    protected int graduationYear;

    /**
     * Setters for instance variables
     */
    public void setName(String name){this.name = name;}
    public void setGraduationYear(int year){
        if (year > 1769 && year < 2100){
            graduationYear = year;
        }
    }

    public static void main(String[] args) {
        Student02 kelly = new Student02();
        kelly.setName("Kelly");
        kelly.setGraduationYear(2200);
        System.out.println("Name: " + kelly.name + ", Year: " + kelly.graduationYear);
    }
}
