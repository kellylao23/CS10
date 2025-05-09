public class StudentTrackerApp {
    public static void main(String[] args) {
        int numberOfStudents = 3;
        Student07[] students = new Student07[numberOfStudents];
        students[0] = new Student07("Alice", 2027);
        students[1] = new Student07("Bob", 2024);
        students[2] = new Student07("Charlie", 2025);

        //print students with for-each loop
        System.out.println("Before studying");
        for (Student07 student : students) {
            System.out.println(student);
        }

        //randomly select students to study to simulate an actual application
        for (int i = 0; i < 10; i++) {
            //pick random student
            int index = (int)(Math.random() * numberOfStudents);
            Student07 student = students[index];

            //add random studying time between 0 and 5 hours
            double time = Math.random() * 5;
            student.study(time);
        }

        //print students after studying with C-style for loop
        System.out.println("After studying");
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
    }
}
