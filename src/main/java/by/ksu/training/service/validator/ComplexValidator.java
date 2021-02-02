package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class ComplexValidator extends BaseValidator<Complex> implements Validator<Complex> {
    @Override
    public Map<String, String> getWarningMap() {
        return warningMap;
    }

    @Override
    public Complex getInvalid() {
        return invalid;
    }

    @Override
    public Complex validate(HttpServletRequest request) throws IncorrectFormDataException {
        final int maxTitleLength = 255;
        final int minTitleLength = 3;
        Complex complex = new Complex();


        String title = request.getParameter(AttrName.TITLE);
        title = validateText(AttrName.TITLE, title, minTitleLength, maxTitleLength);
        complex.setTitle(title);


        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        if (user.getRole() == Role.TRAINER) {
            String stringId = request.getParameter(AttrName.VISITOR_ID);
            int id;
            if (stringId == null || stringId.isEmpty()) {
               addWarning(AttrName.VISITOR_ID, "message.warning.emptyParameter");
            } else {
                try {
                    id = Integer.parseInt(stringId);
                    complex.setVisitorFor(new User(id));
                    complex.setTrainerDeveloped(user);
                } catch (NumberFormatException e) {
                    addWarning(AttrName.VISITOR_ID, "message.warning.invalid");
                }
            }
        }

        if (warningMap != null) {
            invalid = complex;
            throw new IncorrectFormDataException(warningMap.keySet().toString(), warningMap.values().toString());
        }
        return complex;
    }


    private String validateText(String paramName, String text, int minLength, int maxLength) {
        text.replaceAll("<","&lt;").replaceAll(">", "&gt;").trim();

        if (text == null || text.isEmpty()) {
            addWarning("attr." + paramName, "message.warning.emptyParameter");
        } else {
            if (text.length() < minLength) {
                addWarning("attr." + paramName, "message.warning.shortParameter");
            } else if (text.length() > maxLength) {
                addWarning("attr." + paramName, "message.warning.longParameter");
                return text.substring(0, maxLength);
            }
        }
        return text;
    }

    @Override
    public Integer validateId(HttpServletRequest request) throws PersistentException {
        String stringId = request.getParameter(AttrName.COMPLEX_ID);
        if (stringId == null || stringId.isEmpty()) {
            throw new PersistentException(String.format("Empty parameter: %s", AttrName.COMPLEX_ID));
        }
        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new PersistentException(String.format("Record id cannot be read: %s, was found %s", AttrName.COMPLEX_ID, stringId));
        }
    }


}
