package rdid.studentssys.model;

import java.time.LocalDate;
import java.util.*;

public class Student {
    private final int id;
    private String name;
    private String surname;
    private String email;
    private List<Group> groups;
    private Map<LocalDate, Boolean> attendance;

    public Student(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.groups = new ArrayList<>();
        this.groups.add(GroupManager.getInstance().getDefaultGroup()); // Adding student to default group
    }

    public void markAttendance(LocalDate date, boolean present) {
        if (attendance == null) {
            attendance = new HashMap<>();
        }
        attendance.put(date, present);
        for (Group group : groups) {
            group.updateAttendance(this, attendance);
        }
    }

    // Getters and setters
    public final int getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final String getSurname() {
        return surname;
    }

    public final String getEmail() {
        return email;
    }

    public final Map<LocalDate, Boolean> getAttendance() {
        return attendance;
    }

    public List<Group> getGroup() {
        if(groups == null) {
            System.out.println("Student is not assigned to any group.");
            return null;
        }
        return groups;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroup(Group group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        if (group != null) {
            if(!groups.contains(group)) {
                groups.add(group);
            } else {
                System.out.println("Student is already in this group.");
            }
        } else {
            System.out.println("Group cannot be null.");
        }
    }



}