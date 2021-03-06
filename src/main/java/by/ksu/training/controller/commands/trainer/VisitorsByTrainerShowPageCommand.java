package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Entity;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Prepares data to show on page: visitors of trainer.
 *
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class VisitorsByTrainerShowPageCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(VisitorsByTrainerShowPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        SubscriptionService subscriptionService = factory.getService(SubscriptionService.class);
        AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);

        //TODO вытягиваю лишние данные. Подумать как обойтись без этого
        List<Subscription> subscriptions = subscriptionService.findAllActive();
        List<User> visitors = assignedTrainerService.findVisitorsByTrainer(user);
        List<Integer> activeVisitorsId = visitors.stream()
                .map(Entity::getId).collect(Collectors.toList());
        List<Subscription> activeVisitorSubscriptionList = subscriptions.stream()
                .filter(subscription -> activeVisitorsId.contains(subscription.getVisitor().getId()))
                .collect(Collectors.toList());

        request.setAttribute("lst", activeVisitorSubscriptionList);
        return new ForwardState("visitor/list.jsp");
    }
}
