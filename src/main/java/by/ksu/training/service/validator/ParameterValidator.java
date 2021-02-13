package by.ksu.training.service.validator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public interface ParameterValidator extends WarningMap {
    LocalDate validateDate(String attrName, HttpServletRequest request);
    String validateText(String attrName, HttpServletRequest request);
    Integer validateInt(String attrName, HttpServletRequest request);
}
