package by.ksu.training.service.validator;

import by.ksu.training.entity.Entity;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class NewSubscriptionValidator implements Validator{
    private static final String BEGIN_DATE = "beginDate";
    private static final String PREVIOUS_END_DAY = "previousEndDate";
    private static final String PERIOD = "period";
    @Override
    public Entity validate(HttpServletRequest request) throws IncorrectFormDataException {
        Subscription subscription = new Subscription();
        String beginDate = request.getParameter(BEGIN_DATE);
        String previousEndDate = request.getParameter(PREVIOUS_END_DAY);
        String period = request.getParameter(PERIOD);

        try {
            if (beginDate != null) {
                LocalDate newBegin = LocalDate.parse(beginDate);
                if (previousEndDate != null) {
                    LocalDate previousEnd = LocalDate.parse(previousEndDate);
                    // начало новой подписки позже чем старая закончится
                    if (!newBegin.isAfter(previousEnd)) {
                        throw new IncorrectFormDataException(BEGIN_DATE, beginDate);
                    }
                }
                // начало новой подписки не ранее сегодняшнего дня
                LocalDate today = LocalDate.now();
                if (newBegin.isBefore(today)) {
                    throw new IncorrectFormDataException(BEGIN_DATE, beginDate);
                }

                subscription.setBeginDate(newBegin);
            } else {
                throw new IncorrectFormDataException(BEGIN_DATE, beginDate);
            }
        }catch (DateTimeParseException e) {
            throw new IncorrectFormDataException(BEGIN_DATE, beginDate);
        }

        try {
            if (period != null) {
                int month = Integer.parseInt(period);
                LocalDate endDate = subscription.getBeginDate().plusMonths(month);
                subscription.setEndDate(endDate);
            }
        }catch (NumberFormatException e) {
            throw new IncorrectFormDataException(PERIOD, period);
        }

        return subscription;
    }
}
