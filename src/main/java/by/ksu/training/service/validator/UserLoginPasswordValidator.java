package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class UserLoginPasswordValidator extends BaseValidator<User> implements Validator<User> {
    public static final String REGEX_LOGIN = "[A-Za-z0-9_\\-]{5,}";

    @Override
    public Map<String, String> getWarningMap() {
        return warningMap;
    }

    @Override
    public User getInvalid() {
        return invalid;
    }

    @Override
    public Integer validateId(HttpServletRequest request) {
        throw new UnsupportedOperationException();
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

        user.setPassword(password);

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
