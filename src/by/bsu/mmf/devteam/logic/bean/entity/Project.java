package by.bsu.mmf.devteam.logic.bean.entity;

public class Project {
    private int id;
    private String name;
    private int specification;
    private int time;
    private int manager;

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

    public int getSpecification() {
        return specification;
    }

    public void setSpecification(int specification) {
        this.specification = specification;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }
}
