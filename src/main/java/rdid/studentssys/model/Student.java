package rdid.studentssys.model;

import rdid.studentssys.controller.ControllerLocator;


import java.time.LocalDate;
import java.util.*;

public class Student {
    private static int idCounter = 0;
    private int id;
    private String name;
    private String surname;
    private String email;
    private List<Group> groups;
    private Map<LocalDate, Boolean> attendance;
    private List<Integer> ids = new ArrayList<>();

    private static List<Student> students = new ArrayList<>();

    public Student(String name, String surname, String email) {
        if(!StudentManager.getInstance().alreadyHere(name, surname, email)) { // No duplicates
            while (ids.contains(idCounter)) {
                idCounter++;
            }
            this.id = idCounter;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.groups = new ArrayList<>();
            GroupManager.getInstance().getDefaultGroup().addStudent(this); // Adding student to default group and add default group to groups
            StudentManager.getInstance().addStudent(this);
            ControllerLocator.getHomeController().updateDashboard();
        }
    }

    public Student(String name, String surname, String email, String[] groupArr) {
        if(!StudentManager.getInstance().alreadyHere(name, surname, email)) { // No duplicates
            while (ids.contains(idCounter)) {
                idCounter++;
            }
            this.id = idCounter;
            this.ids.add(id);
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.groups = new ArrayList<>();
            for(String group : groupArr) {
                if(GroupManager.getInstance().getGroupByName(group) != null) {
                    GroupManager.getInstance().getGroupByName(group).addStudent(this);
                } else {
                    GroupManager.getInstance().createGroup(group);
                    GroupManager.getInstance().getGroupByName(group).addStudent(this);
                }
            }
            StudentManager.getInstance().addStudent(this);
            ControllerLocator.getHomeController().updateDashboard();
        }
    }
    public Student(int id, String name, String surname, String email, String[] groupArr) {
        if(!StudentManager.getInstance().alreadyHere(name, surname, email)) { // No duplicates
            this.id = id;
            this.ids.add(id);
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.groups = new ArrayList<>();
            for(String group : groupArr) {
                if(GroupManager.getInstance().getGroupByName(group) != null) {
                    GroupManager.getInstance().getGroupByName(group).addStudent(this);
                } else {
                    GroupManager.getInstance().createGroup(group);
                    GroupManager.getInstance().getGroupByName(group).addStudent(this);
                }
            }
            StudentManager.getInstance().addStudent(this);
            ControllerLocator.getHomeController().updateDashboard();
        }
    }
    public Student(int id, String name, String surname, String email) {
        if(!StudentManager.getInstance().alreadyHere(name, surname, email)) { // No duplicates
            this.id = id;
            this.ids.add(id);
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.groups = new ArrayList<>();
            GroupManager.getInstance().getDefaultGroup().addStudent(this); // Adding student to default group and add default group to groups
            this.groups.add(GroupManager.getInstance().getDefaultGroup());
            StudentManager.getInstance().addStudent(this);
            ControllerLocator.getHomeController().updateDashboard();
        }
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

    public List<String> getGroupNames() {
        List<String> groupNames = new ArrayList<>();
        if (groups != null) {
            for (Group group : groups) {
                groupNames.add(group.getGroupName());
            }
        }
        return groupNames;
    }

    public void removeGroup(Group group) {
        if (groups != null && groups.contains(group)) {
            groups.remove(group);
            group.removeStudent(this);
        } else {
            System.out.println("Student is not in this group.");
        }
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

    public void setAttendance(Map<LocalDate, Boolean> attendance) {
        this.attendance = attendance;
        for (Group group : groups) {
            group.updateAttendance(this, attendance);
        }
    }

}
