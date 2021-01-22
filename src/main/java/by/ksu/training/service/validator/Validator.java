package by.ksu.training.service.validator;

import by.ksu.training.entity.Entity;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Validator<Type extends Entity> {
    static final String REMOVE = "remove"; //todo move to another place

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

//    Integer validateId(HttpServletRequest request) throws IncorrectFormDataException;
//    {
//        try {
//            return Integer.parseInt(stringId);
//        } catch (NumberFormatException e) {
//            throw new IncorrectFormDataException("id", stringId);
//        }
//    }
}
