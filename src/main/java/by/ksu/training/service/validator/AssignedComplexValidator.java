package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class AssignedComplexValidator implements Validator<AssignedComplex> {
    @Override
    public AssignedComplex validate(HttpServletRequest request) throws IncorrectFormDataException {
        String visitorId = request.getParameter(AttrName.VISITOR_ID);
        String complexId = request.getParameter(AttrName.COMPLEX_ID);
        String dateExpected = request.getParameter(AttrName.DATE_EXPECTED);

        AssignedComplex assignedComplex = new AssignedComplex();


        try {
                int id = Integer.parseInt(visitorId);
                assignedComplex.setVisitor(new User(id));
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(AttrName.VISITOR_ID, visitorId);
        }

        try {
            int id = Integer.parseInt(complexId);
            assignedComplex.setComplex(new Complex(id));
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(AttrName.COMPLEX_ID, complexId);
        }

        try {
            if (dateExpected != null) {
                LocalDate localDate = LocalDate.parse(dateExpected);
                assignedComplex.setDateExpected(localDate);
            } else {
                throw new IncorrectFormDataException(AttrName.DATE_EXPECTED, dateExpected);
            }
        }catch (DateTimeParseException e) {
            throw new IncorrectFormDataException(AttrName.DATE_EXPECTED, dateExpected);
        }
        return assignedComplex;

    }

    @Override
    public Integer validateId(HttpServletRequest request) throws PersistentException {
        String stringId = request.getParameter(AttrName.ASSIGNED_COMPLEX_ID);
        if (stringId == null || stringId.isEmpty()) {
            throw new PersistentException(String.format("Record id was not found: %s", AttrName.ASSIGNED_COMPLEX_ID));
        }
        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new PersistentException(String.format("Record id cannot be read: %s, was found %s", AttrName.ASSIGNED_COMPLEX_ID, stringId));
        }
    }

//    @Override
//    public Integer validateId(HttpServletRequest request) throws IncorrectFormDataException {
//        String stringId = request.getParameter(AttrName.ASSIGNED_COMPLEX_ID);
//        try {
//            if (stringId != null) {
//                return Integer.parseInt(stringId);
//            } else {
//                throw new IncorrectFormDataException("id", stringId);
//            }
//        } catch (NumberFormatException e) {
//            throw new IncorrectFormDataException("id", stringId);
//        }
//    }
}
