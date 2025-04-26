package rdid.studentssys.model;

public class Student {
    private String id;
    private String name;
    private String surname;
    // private Group group;

    // Constructor
    public Student(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    /*
    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
     */
}