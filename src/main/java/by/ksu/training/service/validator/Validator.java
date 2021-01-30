package by.ksu.training.service.validator;

import by.ksu.training.entity.Entity;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Validator<Type extends Entity> {
    static final String REMOVE = "remove"; //todo move to another place
    static final String ADD_ID = "addId"; //todo move to another place

    default Map<String, String> getWarningMap(){throw new UnsupportedOperationException();}; //TODO реализовать везде
    default  Type getInvalid() {throw new UnsupportedOperationException();};

    Type validate(HttpServletRequest request) throws IncorrectFormDataException;

    default List<Integer> validateRemoveId(HttpServletRequest request) throws IncorrectFormDataException {
        String[] removeId = request.getParameterValues(REMOVE);
        List<Integer> listId = new ArrayList<>();
        if (removeId != null) {
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
        return List.of();
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
            return Integer.parseInt(attribute);
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(attrName, attribute);
        }
    }

    //TODO remove this
    Integer validateId(HttpServletRequest request) throws PersistentException ;

}
