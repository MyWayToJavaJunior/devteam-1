package by.bsu.mmf.devteam.filter;

import by.bsu.mmf.devteam.logic.bean.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
@WebFilter(dispatcherTypes = {DispatcherType.FORWARD},
           urlPatterns = {"/"})
public class AuthorizedRedirectFilter implements Filter {
    private User user;
    private static final String FORWARD = "controller?executionCommand=REDIRECT";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            response.sendRedirect(request.getContextPath() + FORWARD);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        user = null;
    }

}
