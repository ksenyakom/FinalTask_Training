package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.Entity;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Validator<T extends Entity> {

    default Map<String, String> getWarningMap() {
        throw new UnsupportedOperationException();
    } //TODO реализовать везде

    default T getInvalid() {
        throw new UnsupportedOperationException();
    }

    T validate(HttpServletRequest request) throws IncorrectFormDataException;

    List<Integer> validateListId(String attrName, HttpServletRequest request) throws PersistentException;

    Integer validateIntAttr(String attrName, HttpServletRequest request) throws PersistentException;

    Integer validateId(HttpServletRequest request) throws PersistentException;

}
