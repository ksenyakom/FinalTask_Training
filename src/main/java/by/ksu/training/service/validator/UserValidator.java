package by.ksu.training.service.validator;

import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class UserValidator implements Validator<User> {
    public static final String REGEX_LOGIN = "[A-Za-z0-9_\\-]{5,}";
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_.+\\-]+@[a-zA-Z0-9\\-]+\\.[a-zA-Z0-9\\-.]+";

    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        User user = new User();

        String login = request.getParameter("login");
        if (login != null && login.matches(REGEX_LOGIN)) {
            user.setLogin(login);
        } else {
            throw new IncorrectFormDataException("login", login);
        }

        String password = request.getParameter("password");
        if (password != null && password.length() >= 5) {
            user.setPassword(password);
        } else {
            throw new IncorrectFormDataException("password", password);
        }

        String email = request.getParameter("email");
        if (email != null && email.matches(REGEX_EMAIL)) {
            user.setEmail(email);
        } else {
            throw new IncorrectFormDataException("email", email);
        }

        return user;
    }
}
