package by.ksu.training.controller.commands.common;

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
            request.setAttribute("success_message", "Data saved successfully"); //TODo видимо я теряю это сообщение
            return new ResponseState("/index.jsp", true);

        } catch (IncorrectFormDataException e) {
            request.setAttribute("warning_message", "You entered incorrect data: " + e.getMessage());
            return new ResponseState("person/edit.jsp"); // returnBack
        } catch (PersistentException e) {
            logger.error("Exception while changing person data of user {}",
                    ((User)request.getSession().getAttribute("authorizedUser")).getLogin(), e);
            request.setAttribute("error_message", "Exception in command!! " + e.getMessage());
            return null; //error state
        }

    }
}
