package rdid.studentssys.model;

import rdid.studentssys.controller.ControllerLocator;

import java.util.*;

public class GroupManager {

    private static GroupManager instance;
    private Group defaultGroup;
    private List<Group> allGroups = new ArrayList<>();

    private GroupManager() {
        this.defaultGroup = new Group("Default Group");
        this.allGroups.add(defaultGroup);
    }

    public static GroupManager getInstance() {
        if (instance == null) {
            instance = new GroupManager();
        }
        return instance;
    }

    public Group getDefaultGroup() {
        return defaultGroup;
    }

    public List<Group> getAllGroups() {
        return allGroups;
    }

    public Group getGroupByName(String groupName) {
        for (Group group : allGroups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null; // Group not found
    }

    public void createGroup(String groupName) {
        Group newGroup = new Group(groupName);
        allGroups.add(newGroup);
        ControllerLocator.getHomeController().updateDashboard();
    }

    public void deleteGroup(String groupName){
        Group deleteGroup = getGroupByName(groupName);
        if (deleteGroup != null) {
            allGroups.remove(deleteGroup);
            for (Student student : deleteGroup.getStudents()) {
                defaultGroup.addStudent(student);
            }
            deleteGroup = null; // Clear reference
            System.out.println("Group deleted successfully: " + groupName);
        } else {
            System.out.println("Group not found.");
        }
        ControllerLocator.getHomeController().updateDashboard();
    }

    public void deleteGroup(Group group){
        if (group != null) {
            allGroups.remove(group);
            for (Student student : group.getStudents()) {
                // Remove student from the group and add to default group
                // group.removeStudent(student);
                student.removeGroup(group);
                if(student.getGroup().isEmpty()) {
                    defaultGroup.addStudent(student);
                }
            }
            String groupName = group.getGroupName();
            group = null; // Clear reference
            System.out.println("Group deleted successfully: " + groupName);
        } else {
            System.out.println("Group not found.");
        }
        ControllerLocator.getHomeController().updateDashboard();
    }

    public void addStudentToGroup(Student student, String groupName) {
        Group group = getGroupByName(groupName);
        if (group == null) {
            group = new Group(groupName);
            allGroups.add(group);
        }
        group.addStudent(student);
        System.out.println("Student added to group: " + group.getGroupName());
        if (group != GroupManager.getInstance().getDefaultGroup()) {
            removeStudentFromGroup(student, GroupManager.getInstance().getDefaultGroup().getGroupName());
        }
    }

    public void removeStudentFromGroup(Student student, String groupName) {
        Group group = getGroupByName(groupName);
        if (group != null) {
            group.removeStudent(student);
            System.out.println("Student removed from group.");
        }
        if (student.getGroup() == null) {
            defaultGroup.addStudent(student);
            System.out.println("Student added to default group.");
        }
    }

    public void removeStudentFromAllGroups(Student student) {
        for (Group group : allGroups) {
            if (group.getStudents().contains(student)) {
                group.removeStudent(student);
            }
        }
    }


}
