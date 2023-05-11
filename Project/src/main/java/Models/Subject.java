package Models;

import Controllers.SubjectsController;

public class Subject {
    SubjectsController sc;
    private int id;
    private String name;
    private String description;
    private int year;

    public Subject(int id, String name, String description, int year) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
