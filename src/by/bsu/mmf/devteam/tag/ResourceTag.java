package by.bsu.mmf.devteam.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceTag extends TagSupport {
    private static final String RESOURCE_PATH = "by.bsu.mmf.devteam.resource.Resource";
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag() throws JspException {
        ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_PATH, Locale.forLanguageTag("en"));
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(resource.getString(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}


