/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student05 - adds instance variables to track hours spent studying and in class
 *
 * @author Kelly Lao, Dartmouth CS10, Winter 2025
 */
public class Student05 {
    protected String name;
    protected int graduationYear;
    protected double studyHours;
    protected double classHours;

    public Student05(){
        //default constructor: you get this by default
    }

    public Student05(String name, int year) {
        this.name = name;
        graduationYear = year;
    }

    /**
     * Setters for instance variables
     */
    public void setName(String name) { this.name = name; }
    public void setYear(int year) {
        //only accept valid years
        if (year > 1769 && year < 2100) {
            graduationYear = year;
        }
    }

    /**
     * Getters for instance variables
     */
    public String getName() { return name; }
    public int getGraduationYear() { return graduationYear; }
    public double getStudyHours() { return studyHours; }
    public double getClassHours() { return classHours; }

    /**
     * adds hoursSpent to the studyHours to track time this student spent studying
     * @param hoursSpent- number of hours spent studying (can have decimal component)
     * @return - total number of hours spent studying including the new hours passedd in
     */
    public double study(double hoursSpent){
        System.out.println("Hi Mom! It's " + name + ". I'm studying!");
        studyHours += hoursSpent;
        return studyHours;
    }

    /**
     * adds hoursSpent to the classHours to track time this student spent in class
     * @param hoursSpent - number of hours spent in class (can have decimal component)
     * @return - total number of hours spent in class including the new hours passed in
     */
    public double attendClass(double hoursSpent){
        System.out.println("Hi Dad! It's " + name + ". I'm in class!");
        classHours += hoursSpent;
        return classHours;
    }

    public static void main(String[] args) {
        Student05 abby = new Student05(); //calls first constructor, default instance variables
        Student05 alice = new Student05("Alice", 2027); //calls second constructor
        alice.study(1.5);
        alice.attendClass(1.1);
    }


}
