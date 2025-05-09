/**
 * Student series demonstrates encapsulation by representing a student in a class
 * Student0 - base example with student ID, name, and graduation yearSett
 *
 * @author Kelly Lao, Dartmouth CS10, Winter 2025
 */
public class Student0 { // classes start with capital letter
    // instance variables, use camelCase, initializes to null or 0
    String name;
    int graduationYear;
    public static void main(String[] args) {
        Student0 kelly = new Student0();
        kelly.name = "Kelly"; // setting can be done using dot operators, should NOT be done in Java
        kelly.graduationYear = 2028;
        System.out.println("Name: " + kelly.name + ", Year: " + kelly.graduationYear);
    }
}
