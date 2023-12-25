public class Student extends Person {

    String program;

    public Student() {
        super();
    }

    public Student(String name, String role) {
        super(name, role);
    }



    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
