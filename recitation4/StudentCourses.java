import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileReader;

/**
 * Programming Drill 4 (PD-4)
 * Read a data file of courses students have taken,
 * create a Map so that given a student name, allows fast lookup of courses that
 * student has taken
 *
 * Assume after each term the Registrar adds a line new to the data file
 * for each student with the courses the students have passed that term in
 * the following format:
 * Student name:<course 1 name>,<course 2 name>, ...
 *
 * Students will appear in the data file multiple times, once for each term
 * that they passed a class
 *
 * See registrar.txt for an example data file.
 *
 * @author Nand Patel
 * @author Tim Pierson, Dartmouth CS10, Winter 2025
 */

public class StudentCourses {
    private Map<String, List<String>> data; //student name as key, list of courses as value

    /**
     * Constructor for the PD4 class. Initialize data hashmap, then read fileName
     * to load data
     *
     * @param fileName name to the file of data we want to read
     * @throws Exception - thrown if fileName not found
     */
    public StudentCourses(String fileName) throws Exception {
        BufferedReader reader;
        String line;

        //initialize data Map
        data = new HashMap<String, List<String>>();

        //read data file called fileName line by line
        //TODO: instantiate BufferedReader called reader to read fileName
        reader = new BufferedReader(new FileReader(fileName));


        while ((line = reader.readLine()) != null) {
            processLine(line); //could put processing logic here, instead we call a method to do it
        }
    }

    /**
     * Processes a single line from the file and updates the data Map
     * @param line A line from the data file containing student data
     * @throws Exception - throw Exception if line not formatted with name and at least one course
     */
    public void processLine(String line) throws Exception {
        //TODO: your code here
        List<String> classes = new ArrayList<>();
        String[] first = line.split(":");
        String[] c = first[1].split(",");
        for (int i = 0; i < c.length; i++){
            classes.add(c[i]);
        }
        if (data.containsKey(first[0])){
            List<String> update = data.get(first[0]);
            for (String n: classes){
                update.add(n);
            }
        }else{
            data.put(first[0], classes);
        }
    }

    /**
     * Given a student name, return a String of all courses they have taken or "No course taken" if student not found
     * in data Map
     * @param studentName - name of student (key to data Map)
     * @return - String of all courses taken by the student
     */
    public String getCourses(String studentName) {
        //TODO: your code here
        String s = "";
        List<String> courses = data.get(studentName);
        if (courses == null || courses.isEmpty()) {
            return "No course taken";
        }
        s += studentName + ": ";
            for (int i = 0; i < courses.size()-1; i++){
                s += courses.get(i) + ", ";
            }
            s += courses.get(courses.size()-1);
        return s;
    }

    public static void main(String[] args) throws Exception {
        try {
            StudentCourses studentCourses = new StudentCourses("inputs/registrar.txt");
            System.out.println("Alice's courses:");
            System.out.println(studentCourses.getCourses("Alice")); //[CS1, MATH8, COGS1, CS10, MATH13, ECON1]

            System.out.println("Frank's courses");
            System.out.println(studentCourses.getCourses("Frank")); //no courses
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}
