package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class NewSubscriptionValidator extends BaseValidator<Subscription> implements Validator<Subscription> {


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
        throw new UnsupportedOperationException();
    }

    @Override
    public Subscription validate(HttpServletRequest request) throws IncorrectFormDataException {
        Subscription subscription = new Subscription();
        String beginDate = request.getParameter(AttrName.BEGIN_DATE);
//        String previousSubscriptionEndDate = request.getParameter(AttrName.PREVIOUS_END_DAY);
        String period = request.getParameter(AttrName.PERIOD);

        LocalDate newBegin = validateDate(AttrName.BEGIN_DATE, beginDate);
        // начало новой подписки не ранее сегодняшнего дня
        if (newBegin != null) {
            LocalDate today = LocalDate.now();
            if (newBegin.isBefore(today)) {
                addWarning("error", "message.warning.new_subscription_must_be_later");
            }
        }

//        // начало новой подписки позже чем старая закончится
//        if (previousSubscriptionEndDate != null) {
//            LocalDate previousEnd = validateDate(AttrName.PREVIOUS_END_DAY, previousSubscriptionEndDate);
//            if (newBegin != null && previousEnd != null) {
//                if (newBegin.isBefore(previousEnd)) {
//                    addWarning("error", "message.warning.new_subscription_must_be_later");
//                }
//            }
//        }
        subscription.setBeginDate(newBegin);
        try {
            if (period != null) {
                int month = Integer.parseInt(period);
                LocalDate endDate = subscription.getBeginDate().plusMonths(month);
                subscription.setEndDate(endDate);
            }
        } catch (NumberFormatException e) {
            addWarning(AttrName.PERIOD, "message.warning.parameter_not_correct");
        }

        if (warningMap != null) {
            invalid = subscription;
            throw new IncorrectFormDataException(warningMap.keySet().toString(), warningMap.values().toString());
        }
        return subscription;
    }

    private LocalDate validateDate(String attrName, String date) {
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
