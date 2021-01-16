package by.ksu.training.service.validator;

import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 */
public class SubscriptionValidator implements Validator<Subscription> {
    private static final String BEGIN_DATE = "beginDate";
    private static final String END_DATE = "endDate";
    private static final String PRICE = "price";
    private static final String SUBSCRIPTION_ID = "subscriptionId";
    private static final String VISITOR_ID = "visitorId";
//yyyy-mm-dd
    @Override
    public Subscription validate(HttpServletRequest request) throws IncorrectFormDataException {
        Subscription subscription = new Subscription();
        String beginDate = request.getParameter(BEGIN_DATE);
        String endDate = request.getParameter(END_DATE);
        String price = request.getParameter(PRICE);
        String subscriptionId = request.getParameter(SUBSCRIPTION_ID);
        String visitorId = request.getParameter(VISITOR_ID);

        try {
            if (subscriptionId != null ) {
                subscription.setId(Integer.parseInt(subscriptionId));
            } else {
                throw new IncorrectFormDataException(SUBSCRIPTION_ID, subscriptionId);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(SUBSCRIPTION_ID, subscriptionId);
        }

        try {
            if (visitorId != null ) {

                subscription.setVisitor(new User(Integer.parseInt(visitorId)));
            } else {
                throw new IncorrectFormDataException(VISITOR_ID, visitorId);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(VISITOR_ID, visitorId);
        }

        try {
            if (price != null ) {

                subscription.setPrice(new BigDecimal(price));
            } else {
                throw new IncorrectFormDataException(PRICE, price);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(PRICE, price);
        }


// сделать даты

        return subscription;
    }
}
