package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Person;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.PersonService;
import by.ksu.training.service.validator.PersonValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Update person command.
 *
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class PersonUpdateCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(PersonUpdateCommand.class);

    /**
     * Update person by values which came with parameters from request.
     *
     * @Author Kseniya Oznobishina
     * @Date 17.01.2021
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<Person> validator = new PersonValidator();
        PersonService personService = factory.getService(PersonService.class);
        ResponseState state;
        Person person;
        try {
            person = validator.validate(request);
            personService.save(person);

            logger.debug("User id={} updated person data." , person.getId());
            state = new RedirectState("my_account.html");
            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.person_saved");

        } catch (IncorrectFormDataException e) {
            logger.debug("User entered invalid data while updating person.", e);
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            request.setAttribute(AttrName.PERSON,  validator.getInvalid());
            state =  new ForwardState("person/edit.jsp");
        }
        return state;
    }
}
