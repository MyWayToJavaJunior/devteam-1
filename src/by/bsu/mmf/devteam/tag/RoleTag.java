package by.bsu.mmf.devteam.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class RoleTag extends TagSupport {
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag() throws JspException {

        return SKIP_BODY;
    }

}
