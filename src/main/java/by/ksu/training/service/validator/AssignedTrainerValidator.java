package by.ksu.training.service.validator;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class AssignedTrainerValidator implements Validator<AssignedTrainer> {
    private static final String VISITOR_ID = "visitorId";
    private static final String TRAINER_ID = "trainerId";


    @Override
    public AssignedTrainer validate(HttpServletRequest request) throws IncorrectFormDataException {
        String visitorId = request.getParameter(VISITOR_ID);
        String trainerId = request.getParameter(TRAINER_ID);
        AssignedTrainer assignedTrainer = new AssignedTrainer();

        try {
            if (visitorId != null) {
                int id = Integer.parseInt(visitorId);
                assignedTrainer.setVisitor(new User(id));
            } else {
                throw new IncorrectFormDataException(VISITOR_ID, visitorId);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(VISITOR_ID, visitorId);
        }

        try {
            if (trainerId != null) {
                int id = Integer.parseInt(trainerId);
                assignedTrainer.setTrainer(new User(id));
            } else {
                throw new IncorrectFormDataException(TRAINER_ID, trainerId);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(TRAINER_ID, trainerId);
        }

        assignedTrainer.setBeginDate(LocalDate.now());
        return assignedTrainer;
    }
}

