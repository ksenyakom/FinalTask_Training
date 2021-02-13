package by.ksu.training.service.validator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A validator for parameters which came for search options.
 *
 * @Author Kseniya Oznobishina
 * @Date 12.02.2021
 */
public class ParameterValidatorImpl implements ParameterValidator {
    private Map<String, String> warningMap;
    @Override
    public Map<String, String> getWarningMap() {
        return warningMap;
    }
    protected void addWarning(String paramName, String message) {
        if (warningMap == null) {
            warningMap = new LinkedHashMap<>();
        }
        warningMap.put(paramName, message);
    }

    @Override
    public LocalDate validateDate(String attrName, HttpServletRequest request) {
        String date = request.getParameter(attrName);
        if (date != null) {
            try {
                return LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                addWarning("attr." + attrName, "message.warning.date_is_not_valid");
            }
        }
        return null;
    }

    @Override
    public String validateText(String attrName, HttpServletRequest request) {
        String text = request.getParameter(attrName);
        final int maxLength = 255;
        if (text != null) {
            text = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }
        if (text == null || text.isEmpty()) {
            return null;
        } else {
            if (text.length() > maxLength) {
                addWarning("attr." + attrName, "message.warning.longParameter");
                return text.substring(0, maxLength);
            }
        }
        return text;
    }

    @Override
    public Integer validateInt(String attrName, HttpServletRequest request) {
        String number = request.getParameter(attrName);

        if (number != null) {
            number = number.trim();
        }
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            addWarning("attr." + attrName, "message.warning.invalid");
        }
        return null;
    }


}
