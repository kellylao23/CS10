public class Student extends Person{
    protected Integer graduationYear;
    double studyHours;
    double classHours;

    public Student(String name, String id){
        super(name, id);
    }

    public Student(String name, String id, int graduationYear, double studyHours, double classHours){
        super(name, id);
        this.graduationYear = graduationYear;
        this.studyHours = studyHours;
        this.classHours = classHours;
    }

    public double study(double studyHours){
        System.out.println("Hi Mom! This is " + name + ". I am studying.");
        this.studyHours += studyHours;
        return this.studyHours;
    }

    public double attendClass(double classHours){
        System.out.println("Hi Dad! This is  " + name + ". I am in class.");
        this.classHours += classHours;
        return this.classHours;
    }

    @Override
    public String toString(){
        String s = super.toString() + "\n";
        s += "\tGraduation Year: " + graduationYear + "\n";
        s += "\tHours Studying: " + studyHours + "\n";
        s += "\tHours in Class: " + classHours;
        return s;
    }
}
