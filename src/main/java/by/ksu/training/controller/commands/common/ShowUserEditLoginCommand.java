package by.ksu.training.controller.commands.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class ShowUserEditLoginCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(ShowUserEditLoginCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        return new Forward("user/edit_login.jsp");
    }
}
