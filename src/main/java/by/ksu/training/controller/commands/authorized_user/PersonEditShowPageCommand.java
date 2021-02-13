package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prepared data to show on page "person edit".
 *
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class PersonEditShowPageCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(PersonEditShowPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
            PersonService personService = factory.getService(PersonService.class);
            User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
            Person person = personService.findById(user.getId());
            if (person != null) {
                request.setAttribute("person", person);
            }
            return new ForwardState("person/edit.jsp");
    }
}
