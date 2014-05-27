package by.bsu.mmf.devteam.logic.bean.verifiable.builder;

import by.bsu.mmf.devteam.logic.bean.verifiable.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * This builder builds proposed order
 * @author Dmitry Petrovich
 * @since 2.2.0-beta
 */
public class VerifiableBuilder {
    /** Attributes and parameters */
    private static final String SPECIFICATION_NAME = "nameOfNewSpec";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_PROJECT_NAME = "nameOfNewSpec";
    private static final String PARAM_SPECIFICATION_ID = "specId";

    /**
     * This method extract datum from request and returns verifiable object.
     *
     * @param request HttpServletRequest
     * @return Verifiable object
     */
    public static ProposedOrder buildOrder(HttpServletRequest request) {
        ProposedOrder order = new ProposedOrder();
        order.setSpecification(request.getParameter(SPECIFICATION_NAME));
        ArrayList<ProposedJob> jobs = new ArrayList<>();
        try {
            order.setCount(Integer.parseInt(request.getParameter("jobsCount")));
        } catch (NumberFormatException e) {
            order.setCount(0);
        }
        for (int i = 1; i <= order.getCount() ; ++i) {
            ProposedJob job = new ProposedJob();
            job.setName(request.getParameter("job" + i));
            try {
                job.setCount(Integer.parseInt(request.getParameter("count" + i)));
            } catch (NumberFormatException e) {
                job.setCount(0);
            }
            job.setQualification(request.getParameter("qualification" + i));
            jobs.add(job);
        }
        order.setJobs(jobs);
        return order;
    }

    public static PreparedProject buildProject(HttpServletRequest request) {
        PreparedProject project = new PreparedProject();
        project.setName(request.getParameter(PARAM_PROJECT_NAME));
        try {
            project.setId(Integer.parseInt(request.getParameter(PARAM_SPECIFICATION_ID)));
        } catch (NumberFormatException e) {
            project.setId(0);
        }
        Enumeration<String> names = request.getParameterNames();
        ArrayList<PreparedJob> jobs = new ArrayList<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.length() > 4) {
                if (name.substring(0, 4).equals("cost")) {
                    PreparedJob job = new PreparedJob();
                    try {
                        job.setId(Integer.parseInt(name.substring(4)));
                    } catch (NumberFormatException e) {
                        job.setId(0);
                    }
                    String[] mails = request.getParameterValues("employees" + job.getId());
                    if (mails != null) {
                        job.setEmployees(new ArrayList<String>(Arrays.asList(mails)));
                    } else {
                        job.setEmployees(null);
                    }
                    try {
                        job.setCost(Integer.parseInt(request.getParameter(name)));
                    } catch (NumberFormatException e) {
                        job.setCost(0);
                    }
                    jobs.add(job);
                }
            }
        }
        project.setJobs(jobs);
        return project;
    }

    /**
     * This method extracts datum from request and returns verifiable object.
     *
     * @param request HttpServletRequest object
     * @return Verifiable SignInFrom object
     */
    public static SignInForm buildSignInForm(HttpServletRequest request) {
        SignInForm form = new SignInForm();
        form.setLogin(request.getParameter(PARAM_NAME_EMAIL));
        form.setPassword(request.getParameter(PARAM_NAME_PASSWORD));
        return form;
    }

}
