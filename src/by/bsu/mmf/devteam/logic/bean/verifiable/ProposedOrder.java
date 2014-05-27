package by.bsu.mmf.devteam.logic.bean.verifiable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.2.0-beta
 */
public class ProposedOrder {
    @NotNull(message = "Name must be set")
    @Size(min = 1, message = "Size must be longer 1")
    private String specification;

    @NotNull
    @Min(value = 1)
    private int count;

    @NotNull
    @Size(min = 1)
    @Valid
    private ArrayList<ProposedJob> jobs;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public ArrayList<ProposedJob> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<ProposedJob> jobs) {
        this.jobs = jobs;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
