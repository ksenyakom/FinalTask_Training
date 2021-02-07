package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Changes language, saves in cookies.
 *
 * @Author Kseniya Oznobishina
 * @Date 07.01.2021
 */
public class ChangeLanguageCommand extends Command {
    private static Logger logger = LogManager.getLogger(ChangeLanguageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        //TODO это все надо сделать в js
        String language = request.getParameter(AttrName.LANGUAGE);
        String page = request.getParameter(AttrName.PAGE);

        if (language != null) {
            Cookie cookie = new Cookie(AttrName.LANGUAGE, language);
            response.addCookie(cookie);
        }

        // page.isEmpty if user just came and changes language from first time loaded page index
        if (!page.isEmpty()) {
            String contextPath = request.getContextPath();
            page = page.substring(contextPath.length()+1);
        }
        logger.debug("Redirect to page: {}", page);
        return new RedirectState(page);
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
