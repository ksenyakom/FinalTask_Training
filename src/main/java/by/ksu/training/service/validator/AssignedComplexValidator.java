package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class AssignedComplexValidator extends BaseValidator<AssignedComplex> implements EntityValidator<AssignedComplex> {
    @Override
    public Map<String, String> getWarningMap() {
        return warningMap;
    }

    @Override
    public AssignedComplex getInvalid() {
        return invalid;
    }

    @Override
    public AssignedComplex validate(HttpServletRequest request) throws IncorrectFormDataException {
        AssignedComplex assignedComplex = new AssignedComplex();

        String dateExpected = request.getParameter(AttrName.DATE_EXPECTED);
        LocalDate localDate = validateDate(AttrName.DATE_EXPECTED, dateExpected);
        assignedComplex.setDateExpected(localDate);

        if (warningMap != null) {
            invalid = assignedComplex;
            throw new IncorrectFormDataException(warningMap.keySet().toString(), warningMap.values().toString());
        }

        return assignedComplex;

    }

    private LocalDate validateDate (String attrName, String dateExpected) {
        if (dateExpected != null) {
            try {
                return LocalDate.parse(dateExpected);
            } catch (DateTimeParseException e) {
               addWarning("attr." + attrName, "message.warning.date_is_not_valid");
            }
        } else {
            addWarning("attr." + attrName, "message.warning.date_is_empty");
        }
        return null;

    }

    @Override
    public Integer validateId(HttpServletRequest request) throws PersistentException {
        String stringId = request.getParameter(AttrName.ASSIGNED_COMPLEX_ID);
        if (stringId != null) {
            stringId = stringId.replaceAll("<","&lt;").replaceAll(">", "&gt;").trim();
        }
        if (stringId == null || stringId.isEmpty()) {
            throw new PersistentException(String.format("Record id was not found: %s", AttrName.ASSIGNED_COMPLEX_ID));
        }
        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new PersistentException(String.format("Record id cannot be read: %s, was found %s", AttrName.ASSIGNED_COMPLEX_ID, stringId));
        }
    }

}
