public class CollegeApp{
    public static void main(String[] args){
        int numberOfPeople = 5;
        Person[] people = new Person[numberOfPeople];
        Instructor tjp = new Instructor("Time Pierson", "f00zzz");
        tjp.setDepartment("Computer Science");
        people[0] = tjp;
        people[1] = new Student("Kelly", "f007jt4");
        people[2] = new GraduateStudent("Bob", "f00abc", "Computer Science", "Tim Pierson");
        ((Student)people[2]).graduationYear = 2028;
        people[3] = new InternationalStudent("Charlie", "f00123", "Germany");
        people[4] = new InternationalGraduateStudent("Denise", "f00987");
        ((InternationalGraduateStudent)people[4]).setDepartment("Computer Science");
        ((InternationalGraduateStudent)people[4]).setAdvisorName("Alan Turing");
        ((InternationalGraduateStudent)people[4]).setHomeCountry("Spain");
        for (Person p : people){
            System.out.println(p + "\n");
        }
    }
}
