package by.ksu.training.service.validator;

import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author Kseniya Oznobishina
 * @Date 29.01.2021
 */
public class BaseValidator<T> {
    protected Map<String, String> warningMap;
    protected T invalid;

    protected void addWarning(String paramName, String message) {
        if (warningMap == null) {
            warningMap = new LinkedHashMap<>();
        }
        warningMap.put(paramName,message);
    }

    public List<Integer> validateListId(String attrName, HttpServletRequest request) throws PersistentException {
        String[] arrayId = request.getParameterValues(attrName);
        List<Integer> listId = new ArrayList<>();
        if (arrayId != null) {
            try {
                for (String stringId : arrayId) {
                    int id = Integer.parseInt(stringId);
                    listId.add(id);
                }
                return listId;
            } catch (NumberFormatException e) {
                String error =  Arrays.toString(arrayId).replaceAll("<","&lt;").replaceAll(">", "&gt;").trim();
                throw new PersistentException("User entered incorrect data" + attrName + error);
            }
        } else {
            addWarning(attrName, "message.warning.no_any_record_chosen");
        }
        return List.of();
    }

    public Integer validateIntAttr(String attrName, HttpServletRequest request) throws PersistentException {
        String attribute = request.getParameter(attrName);
        if (attribute != null) {
            attribute = attribute.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }
        try {
            return Integer.parseInt(attribute);
        } catch (NumberFormatException e) {
            throw new PersistentException(String.format("message.warning.parameter_not_correct: %s found %s",
                    attrName, attribute));
        }
    }

}
