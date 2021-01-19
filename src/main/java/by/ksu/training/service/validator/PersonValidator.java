package by.ksu.training.service.validator;

import by.ksu.training.entity.Entity;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class PersonValidator implements Validator<Person> {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String ADDRESS = "address";
    private static final String DATE_OF_BIRTH = "dateOfBirth";
    private static final String PHONE = "phone";
    private static final String SHARE = "share";
    private static final String ACHIEVEMENTS = "achievements";

    @Override
    public Person validate(HttpServletRequest request) throws IncorrectFormDataException {
        Person person = new Person();

        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String patronymic = request.getParameter(PATRONYMIC);
        String address = request.getParameter(ADDRESS);
        String dateOfBirth = request.getParameter(DATE_OF_BIRTH);
        String phone = request.getParameter(PHONE);
        String share = request.getParameter(SHARE);
        String achievements = request.getParameter(ACHIEVEMENTS);

        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        person.setAddress(address);

        try {
            if (dateOfBirth != null) {
                person.setDateOfBirth(LocalDate.parse(dateOfBirth));
            }
        } catch (DateTimeParseException e) {
            throw new IncorrectFormDataException(DATE_OF_BIRTH, dateOfBirth);
        }
        person.setPhone(phone);

        User user = (User) request.getSession().getAttribute("authorizedUser");

        if (user.getRole() == Role.VISITOR) {
            person.setAchievements(share);
        } else if (user.getRole() == Role.TRAINER) {
            person.setAchievements(achievements);
        }

        person.setId(user.getId());
        return person;
    }
}
