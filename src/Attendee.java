public class Attendee extends Person{
    private int id;
    private String name;
    private String role;
    private String guestOf;
    private String program;

    public Attendee(int id, String name, String role, String guestOf, String program) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.guestOf = guestOf;
        this.program = program;
    }

    public Attendee(String name, String role, String guestOf, String program) {
        this.name = name;
        this.role = role;
        this.guestOf = guestOf;
        this.program = program;
    }

    public String getGuestOf() {
        return guestOf;
    }

    public void setGuestOf(String guestOf) {
        this.guestOf = guestOf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
