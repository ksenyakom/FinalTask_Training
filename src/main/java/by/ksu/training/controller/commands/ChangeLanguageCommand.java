package by.ksu.training.controller.commands;

import by.ksu.training.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 07.01.2021
 */
public class ChangeLanguageCommand extends Command {
    private static Logger logger = LogManager.getLogger(ChangeLanguageCommand.class);
    public static final String LANGUAGE = "language";
    public static final String PAGE = "page";

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(LANGUAGE);
        String page = request.getParameter(PAGE);

        if (language != null) {
            Cookie cookie = new Cookie(LANGUAGE, language);
            response.addCookie(cookie);
        }
        request.setAttribute("command", null);
        String contextPath = request.getContextPath();
        page = page.substring(contextPath.length());

        logger.debug("Redirect to page: {}", page);
        return new Forward(page, true);
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
