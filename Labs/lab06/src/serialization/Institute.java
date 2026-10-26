package serialization;

public class Institute implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Faculty[] faculty;

    public Institute(String name) {
        this.name = name;
        this.faculty = null;
    }

    public Faculty[] getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty[] faculty) {
        this.faculty = faculty;
    }

    public void addFaculty(Faculty facultyMember) {
        if (this.faculty == null) {
            this.faculty = new Faculty[] { facultyMember };
        } else {
            Faculty[] newFaculty = new Faculty[this.faculty.length + 1];
            System.arraycopy(this.faculty, 0, newFaculty, 0, this.faculty.length);
            newFaculty[this.faculty.length] = facultyMember;
            this.faculty = newFaculty;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
