package by.bsu.mmf.devteam.logic.bean.entity;

public class Job {
    private int id;
    private String name;
    private int specialist;
    private String qualification;
    private int specification;
    private int cost;
    private int time;

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

    public int getSpecialist() {
        return specialist;
    }

    public void setSpecialist(int specialist) {
        this.specialist = specialist;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSpecification() {
        return specification;
    }

    public void setSpecification(int specification) {
        this.specification = specification;
    }
}
