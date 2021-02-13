package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 */
public class SubscriptionValidator extends BaseValidator<Subscription> implements EntityValidator<Subscription> {

    @Override
    public Map<String, String> getWarningMap() {
        return warningMap;
    }

    @Override
    public Subscription getInvalid() {
        return invalid;
    }

    @Override
    public Integer validateId(HttpServletRequest request) {
        return null;
    }

    @Override
    public Subscription validate(HttpServletRequest request) throws IncorrectFormDataException {
        Subscription subscription = new Subscription();

        String sBeginDate = request.getParameter(AttrName.BEGIN_DATE);
        LocalDate beginDate = validateDate(AttrName.BEGIN_DATE, sBeginDate);
        subscription.setBeginDate(beginDate);

        String sEndDate = request.getParameter(AttrName.END_DATE);
        LocalDate endDate = validateDate(AttrName.END_DATE, sEndDate);
        subscription.setEndDate(endDate);
        if ( endDate.isBefore(beginDate)) {
            addWarning("attr." + AttrName.END_DATE, "message.warning.date_is_not_valid");
        }

        String price = request.getParameter(AttrName.PRICE);
        BigDecimal bigDecimalPrice = validateBigDecimal(AttrName.PRICE,price);
        subscription.setPrice(bigDecimalPrice);


        if (warningMap != null) {
            invalid = subscription;
            throw new IncorrectFormDataException(warningMap.keySet().toString(), warningMap.values().toString());
        }
        return subscription;
    }

    private BigDecimal validateBigDecimal(String attrName, String price) {
        if (price != null) {
            price = price.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }
        try {
            if (price != null ) {
                return new BigDecimal(price);
            } else {
                addWarning("attr." + attrName, "message.warning.emptyParameter");
            }
        } catch (NumberFormatException e) {
            addWarning("attr." + attrName, " message.warning.parameter_not_correct");
        }
        return null;
    }

    public LocalDate validateDate (String attrName, String date) {
        if (date != null) {
            try {
                return LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                addWarning("attr." + attrName, "message.warning.date_is_not_valid");
            }
        } else {
            addWarning("attr." + attrName, "message.warning.date_is_empty");
        }
        return null;

    }
}
