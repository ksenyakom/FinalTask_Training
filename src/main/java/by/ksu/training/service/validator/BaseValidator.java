package by.ksu.training.service.validator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 29.01.2021
 */
public class BaseValidator<Type> {
    protected Map<String, String> warningMap;
    protected Type invalid;

    protected void addWarning(String paramName, String message) {
        if (warningMap == null) {
            warningMap = new LinkedHashMap<>();
        }
        warningMap.put(paramName,message);
    }

//    protected String validateText(String paramName, String text, int minLength, int maxLength) {
//        if (text == null || text.isEmpty()) {
//            addWarning("attr." + paramName, "message.warning.emptyParameter");
//        } else {
//            if (text.length() < minLength) {
//                addWarning("attr." + paramName, "message.warning.shortParameter");
//            } else if (text.length() > maxLength) {
//                addWarning("attr." + paramName, "message.warning.longParameter");
//                return text.substring(0, maxLength);
//            }
//        }
//        return text;
//    }

}
