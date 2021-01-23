package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class ComplexValidator implements Validator<Complex> {
    @Override
    public Complex validate(HttpServletRequest request) throws IncorrectFormDataException {
//
        return null;
    }

    @Override
    public Integer validateId(HttpServletRequest request) throws IncorrectFormDataException {
        String stringId = request.getParameter(AttrName.COMPLEX_ID);
        try {
            if (stringId != null) {
                return Integer.parseInt(stringId);
            } else {
                throw new IncorrectFormDataException(AttrName.COMPLEX_ID, stringId);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(AttrName.COMPLEX_ID, stringId);
        }
    }
}
