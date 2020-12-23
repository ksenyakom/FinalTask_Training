package by.ksu.training.service;

import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionFactory;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceFactoryImpl implements ServiceFactory {
    private static Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);
    private TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) throws PersistentException {
        this.factory = factory;
    }


  //  private final Map<Class<? extends EntityService>, Service> repository = new HashMap<>();

    public Service getService(Class<? extends EntityService> clazz) throws PersistentException {
        ServiceImpl service = null;
            if (clazz == AssignedComplexService.class) { service = new AssignedComplexServiceImpl();}
            else if (clazz == AssignedTrainerService.class) {service = new AssignedTrainerServiceImpl(); }
            else if (clazz == ComplexService.class) {service =  new ComplexServiceImpl();}
            else if (clazz == ExerciseService.class) {service = new ExerciseServiceImpl(); }
            else if (clazz == SubscriptionService.class) {service =  new SubscriptionServiceImpl();}
            else if (clazz == TrainerService.class) {service =  new TrainerServiceImpl();}
            else if (clazz == VisitorService.class) {service =  new VisitorServiceImpl();}
            else if (clazz == UserService.class) {service =  new UserServiceImpl();}
            if (service != null) {
                Transaction transaction = factory.createTransaction();
                service.setTransaction(transaction);
                //TODO где transaction.commit происходит
                //TODO и возвращать connectoin в pool
                return service;
            } else {
                logger.error("No such service: {}", clazz);
                throw new PersistentException("No such service");
            }
        }

    @Override
    public void close() {
        factory.close();
    }
}



//    private AssignedComplexService assignedComplexService = new AssignedComplexServiceImpl();
//    private AssignedTrainerService assignedTrainerService = new AssignedTrainerServiceImpl();
//    private ComplexService complexService = new ComplexServiceImpl();
//    private ExerciseService exerciseService= new ExerciseServiceImpl();
//    private SubscriptionService subscriptionService = new SubscriptionServiceImpl();
//    private TrainerService trainerService = new TrainerServiceImpl();
//    private VisitorService visitorService = new VisitorServiceImpl();
//    private UserService userService = new UserServiceImpl();

//   repository.put(AssignedComplexService.class, new AssignedComplexServiceImpl());
//        repository.put(AssignedTrainerService.class, new AssignedTrainerServiceImpl());
//        repository.put(ComplexService.class, new ComplexServiceImpl());
//        repository.put(ExerciseService.class, new ExerciseServiceImpl());
//        repository.put(SubscriptionService.class, new SubscriptionServiceImpl());
//        repository.put(TrainerService.class, new TrainerServiceImpl());
//        repository.put(VisitorService.class, new VisitorServiceImpl());
//        repository.put(UserService.class, new UserServiceImpl());