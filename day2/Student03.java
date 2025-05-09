public class Student03 {
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

    /**
     * Getters for instance variables
     */
    public String getName(){return name;}
    public int getGraduationYear(){return graduationYear;}

    public static void main(String[] args) {
        Student03 kelly = new Student03();
        kelly.setName("Kelly");
        kelly.setGraduationYear(2028);
        System.out.println("Name: " + kelly.getName() + ", Year: " + kelly.getGraduationYear());
    }
}
