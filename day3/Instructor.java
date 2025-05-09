public class Instructor extends Person{
    boolean tenured;
    int yearsEmployed;
    String department;


    public Instructor(String name, String id) {
        super(name, id);
        //Everything else set to default
    }

    public Instructor(String name, String id, boolean tenured, int yearsEmployed, String department){
        super(name, id);
        this.tenured = tenured;
        this.yearsEmployed = yearsEmployed;
        this.department = department;
    }

    public boolean getTenured(){return tenured;}
    public int getYearsEmployed(){return yearsEmployed;}
    public String getDepartment(){return department;}

    public void setTenured(boolean tenured){this.tenured = tenured;}
    public void setYearsEmployed(int years){yearsEmployed = years;}
    public void setDepartment(String dept){department = dept;}

    @Override
    public String toString(){
        String s = super.toString() + "\n";
        s += "\tTenured: " + tenured + "\n";
        s += "\tYears Employed: " + yearsEmployed + "\n";
        s += "\tDepartment: " + department;
        return s;
    }
}
