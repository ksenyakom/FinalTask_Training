package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.*;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class PersonValidator extends BaseValidator<Person> implements EntityValidator<Person> {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String ADDRESS = "address";
    private static final String DATE_OF_BIRTH = "dateOfBirth";
    private static final String PHONE = "phone";
    private static final String SHARE = "share";
    private static final String ACHIEVEMENTS = "achievements";

    @Override
    public Integer validateId(HttpServletRequest request) {
        return null;
    }

    @Override
    public Map<String, String> getWarningMap() {
        return warningMap;
    }

    @Override
    public Person getInvalid() {
        return invalid;
    }

    @Override
    public Person validate(HttpServletRequest request) throws IncorrectFormDataException {
        Person person = new Person();

        final int maxTextLength = 500;
        final int maxAttrLength = 255;

        String name = request.getParameter(NAME);
        if (name != null && !name.isEmpty()) {
            name = validateText(NAME, name, maxAttrLength);
            person.setName(name);
        } else {
            addWarning("attr." + NAME, "message.warning.emptyParameter");
        }

        String surname = request.getParameter(SURNAME);
        if (surname != null && !surname.isEmpty()) {
            surname = validateText(SURNAME, surname, maxAttrLength);
            person.setSurname(surname);
        }

        String patronymic = request.getParameter(PATRONYMIC);
        if (patronymic != null && !patronymic.isEmpty()) {
            patronymic = validateText(PATRONYMIC, patronymic, maxAttrLength);
            person.setPatronymic(patronymic);
        }

        String address = request.getParameter(ADDRESS);
        if (address != null && !address.isEmpty()) {
            address = validateText(ADDRESS, address, maxAttrLength);
            person.setAddress(address);
        }

        String dateOfBirth = request.getParameter(DATE_OF_BIRTH);
        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            LocalDate localDate = validateDate(DATE_OF_BIRTH, dateOfBirth);
            if (localDate != null) {
                person.setDateOfBirth(localDate);
            }
        }

        String phone = request.getParameter(PHONE);
        if (phone != null) {
            phone = validatePhone(phone);
            person.setPhone(phone);
        }

        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        person.setId(user.getId());

        if (warningMap != null) {
            invalid = person;
            throw new IncorrectFormDataException(warningMap.keySet().toString(), warningMap.values().toString());
        }
        return person;


        /*
        String share = request.getParameter(SHARE);
        String achievements = request.getParameter(ACHIEVEMENTS);
        if (achievements != null && !achievements.isEmpty()) {
            achievements = validateText(ACHIEVEMENTS, achievements, maxTextLength);
            person.setAchievements(achievements);
        }
        if (user.getRole() == Role.VISITOR) {
            person.setAchievements(share);
        } else if (user.getRole() == Role.TRAINER) {
            person.setAchievements(achievements);
        } */


    }

    private String validatePhone(String phone) {
        if (phone != null) {
           phone = phone.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }

        String telPattern = "\\+[0-9]{12}";
        if (phone != null && !phone.isEmpty()) {
            if (phone.matches(telPattern)) {
                return phone;
            } else {
                addWarning("attr.phone", "message.warning.phone_is_not_valid");
            }
        }
        return phone;
    }

    private String validateText(String paramName, String text, int maxLength) {
        if (text != null) {
            text = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }
        if (text != null && text.length() > maxLength) {
                addWarning("attr." + paramName, "message.warning.longParameter");
                return text.substring(0, maxLength);
            }
        return text;
    }

    private LocalDate validateDate(String paramName, String date) {
        try {
            if (date != null) {
                return LocalDate.parse(date);
            }
        } catch (DateTimeParseException e) {
            addWarning("attr." + paramName, "message.warning.date_is_not_valid");
        }
        return null;
    }
}
