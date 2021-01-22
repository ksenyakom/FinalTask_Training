package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.PersonService;
import by.ksu.training.service.validator.PersonValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class SavePersonChangeCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(SavePersonChangeCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Person> validator = new PersonValidator();
            Person person = validator.validate(request);
            PersonService personService = factory.getService(PersonService.class);
            personService.save(person);

            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.person_saved");

            return new RedirectState("index.jsp");

        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("person/edit.jsp");
        } catch (PersistentException e) {
            logger.error("Exception while changing person data of user {}",
                    ((User) request.getSession().getAttribute("authorizedUser")).getLogin(), e);
            request.setAttribute(AttrName.ERROR_MESSAGE, "Exception in command!! " + e.getMessage());
            return new ErrorState();
        }
    }
}
