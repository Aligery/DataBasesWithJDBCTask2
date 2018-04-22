package MainFiles;

public class User {
    private int id;
    private String fname;
    private String lname;
    private String tname;
    private String DateOfBirth;
    private String StudyGroup;

    public User(int id, String firstName, String secondName, String thirdName, String DateOfBirth, String StudyGroup) {
        this.id = id;
        this.fname = firstName;
        this.lname = secondName;
        this.tname = thirdName;
        this.DateOfBirth = DateOfBirth;
        this.StudyGroup=StudyGroup;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setStudyGroup(String studyGroup) {
        StudyGroup = studyGroup;
    }

    public String getFname() {

        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getTname() {
        return tname;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getStudyGroup() {
        return StudyGroup;
    }
}

