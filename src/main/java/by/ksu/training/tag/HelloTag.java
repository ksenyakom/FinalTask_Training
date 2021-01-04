package by.ksu.training.tag;

import by.ksu.training.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @Author Kseniya Oznobishina
 * @Date 02.01.2021
 */
public class HelloTag  extends TagSupport {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String welcomeMessage = "Hello" +  "your role is" ;
            pageContext.getOut().write("<p>" + welcomeMessage + "</p>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
