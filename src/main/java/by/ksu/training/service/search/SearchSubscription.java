package by.ksu.training.service.search;

import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;
import by.ksu.training.service.SubscriptionService;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 11.02.2021
 */

public class SearchSubscription implements Specification<Subscription> {
    private LocalDate from;
    private LocalDate to;
    private String userLogin;

    public SearchSubscription(LocalDate from, LocalDate to, String userLogin) {
        this.from = from;
        this.to = to;
        this.userLogin = userLogin;
    }

    @Override
    public List<Subscription> search(EntityService<Subscription> service) throws PersistentException {
        if (service instanceof SubscriptionService) {
            SubscriptionService subscriptionService = (SubscriptionService) service;
            byte b = checkParameters();
            switch (b) {
                case 0: return List.of();
                case 1: return subscriptionService.findFrom(from);
                case 2: return subscriptionService.findTo(to);
                case 3: return subscriptionService.findFromTo(from,to);
                case 4: return subscriptionService.findByUserLogin(userLogin);
                case 5: return subscriptionService.findFromLogin(from, userLogin);
                case 6: return subscriptionService.findToLogin(to, userLogin);
                case 7: return subscriptionService.findFromToLogin(from, to , userLogin);
                default: return subscriptionService.findAllActive();
            }
        } else {
            throw new PersistentException("Wrong service");
        }
    }

    private byte checkParameters() {
        byte b = 0;
        if (from != null) { // 0001
            b |= 1;
        }
        if (to != null) { // 0010
            b |= 2;
        }
        if (userLogin != null) { // 0100
            b |= 4;
        }

        return b;
    }
}
