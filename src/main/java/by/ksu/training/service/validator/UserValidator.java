package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class UserValidator extends BaseValidator<User> implements Validator<User> {
    public static final String REGEX_LOGIN = "[A-Za-z0-9_\\-]{5,}";
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_.+\\-]+@[a-zA-Z0-9\\-]+\\.[a-zA-Z0-9\\-.]+";


    @Override
    public Integer validateId(HttpServletRequest request) throws PersistentException {
        String stringId = request.getParameter(AttrName.USER_ID);
        if (stringId != null) {
            stringId.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }

        if (stringId == null || stringId.isEmpty()) {
            throw new PersistentException(String.format("Record id was not found: %s", AttrName.USER_ID));
        }
        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new PersistentException(String.format("Record id cannot be read: %s, was found %s", AttrName.USER_ID, stringId));
        }
    }

    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        int minLength = 5;
        int maxLoginLength = 255;
        int maxPasswordLength = 128;

        User user = new User();

        String login = request.getParameter(AttrName.LOGIN);
        login = validateText(AttrName.LOGIN, login, minLength, maxLoginLength);
        if (login != null && !login.matches(REGEX_LOGIN)) {
            addWarning("attr." + AttrName.LOGIN, "message.warning.invalid_login");
        }
        user.setLogin(login);

        String password = request.getParameter(AttrName.PASSWORD);
        password = validateText(AttrName.PASSWORD, password, minLength, maxPasswordLength);

        String password2 = request.getParameter("password2");
        if (!Objects.equals(password, password2)) {
            addWarning("attr." + AttrName.PASSWORD, "message.warning.password_not_match");
        }
        user.setPassword(password);


        String email = request.getParameter(AttrName.EMAIL);
        email = validateText(AttrName.EMAIL, email, minLength, maxLoginLength);

        if (email != null && !email.matches(REGEX_EMAIL)) {
            addWarning("attr." + AttrName.EMAIL, "message.warning.invalid_email");
        }
        user.setEmail(email);

        if (warningMap != null) {
            invalid = user;
            throw new IncorrectFormDataException(warningMap.keySet().toString(), warningMap.values().toString());
        }

        return user;
    }

    private String validateText(String paramName, String text, int minLength, int maxLength) {
        if (text != null) {
            text = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }
        if (text == null) {
            addWarning("attr." + paramName, "message.warning.emptyParameter");
        }
        if (text != null && text.length() > maxLength) {
            addWarning("attr." + paramName, "message.warning.longParameter");
            return text.substring(0, maxLength);
        }

        if (text != null && text.length() < minLength) {
            addWarning("attr." + paramName, "message.warning.shortParameter");
        }
        return text;
    }


}
