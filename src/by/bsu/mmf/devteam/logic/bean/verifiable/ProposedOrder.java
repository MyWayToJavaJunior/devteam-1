package by.bsu.mmf.devteam.logic.bean.verifiable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

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
    @Valid
    private Map<Integer, ProposedJob> jobs;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Map<Integer, ProposedJob> getJobs() {
        return jobs;
    }

    public void setJobs(Map<Integer, ProposedJob> jobs) {
        this.jobs = jobs;
    }

}
