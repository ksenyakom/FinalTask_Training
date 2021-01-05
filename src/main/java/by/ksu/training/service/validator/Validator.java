package by.ksu.training.service.validator;

import by.ksu.training.entity.Entity;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public interface Validator<Type extends Entity> {
    Type validate(HttpServletRequest request) throws IncorrectFormDataException;
}
