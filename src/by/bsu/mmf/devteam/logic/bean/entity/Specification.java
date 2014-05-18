package by.bsu.mmf.devteam.logic.bean.entity;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class Specification {
    private int id;
    private int jobs;
    private String name;
    private String status;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobs() {
        return jobs;
    }

    public void setJobs(int jobs) {
        this.jobs = jobs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
