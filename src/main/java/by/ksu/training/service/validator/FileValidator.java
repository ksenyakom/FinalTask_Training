package by.ksu.training.service.validator;

import by.ksu.training.exception.FileTooBigException;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileValidator {
    List<String> validateNew(HttpServletRequest request) throws PersistentException, FileTooBigException;
    List<String> validateUpdate(HttpServletRequest request) throws PersistentException, FileTooBigException;
}
