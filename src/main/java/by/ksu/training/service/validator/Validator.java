package by.ksu.training.service.validator;

import by.ksu.training.entity.Entity;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Validator<Type extends Entity> {
    static final String REMOVE = "remove"; //todo move to another place
    static final String ADD_ID = "addId"; //todo move to another place

    Type validate(HttpServletRequest request) throws IncorrectFormDataException;

    default List<Integer> validateRemoveId(HttpServletRequest request) throws IncorrectFormDataException {
        String[] removeId = request.getParameterValues(REMOVE);
        List<Integer> listId = new ArrayList<>();
        try {
            for (String stringId : removeId) {
                int id = Integer.parseInt(stringId);
                listId.add(id);
            }
            return listId;
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException("remove id", Arrays.toString(removeId));
        }
    }

    default List<Integer> validateAddId(HttpServletRequest request) throws IncorrectFormDataException {
        String[] addId = request.getParameterValues(ADD_ID);
        List<Integer> listId = new ArrayList<>();
        try {
            for (String stringId : addId) {
                int id = Integer.parseInt(stringId);
                listId.add(id);
            }
            return listId;
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException("add id", Arrays.toString(addId));
        }
    }

    default Integer validateIntAttr(String attrName, HttpServletRequest request) throws IncorrectFormDataException {
        String attribute = request.getParameter(attrName);
        try {
            int value = Integer.parseInt(attribute);
            return value;
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(attrName, attribute);
        }
    }

    Integer validateId(HttpServletRequest request) throws IncorrectFormDataException;

}
