package by.ksu.training.controller.commands.common;

import by.ksu.training.entity.Person;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class ShowPersonEditCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(ShowPersonEditCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            PersonService personService = factory.getService(PersonService.class);
            User user = (User) request.getSession().getAttribute("authorizedUser");
            Person person = personService.findById(user.getId());
            if (person != null) {
                request.setAttribute("person", person);
            }
            return new Forward("person/edit.jsp");

        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message",e.getMessage());
            return null;
        }



    }
}
