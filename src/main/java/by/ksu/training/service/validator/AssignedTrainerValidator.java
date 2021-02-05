package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
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
public class AssignedTrainerValidator extends BaseValidator<AssignedTrainer> implements Validator<AssignedTrainer> {

    @Override
    public Integer validateId(HttpServletRequest request)  {
        return null;
    }

    @Override
    public AssignedTrainer validate(HttpServletRequest request) throws IncorrectFormDataException {
        String visitorId = request.getParameter(AttrName.VISITOR_ID);
        String trainerId = request.getParameter(AttrName.TRAINER_ID);
        AssignedTrainer assignedTrainer = new AssignedTrainer();

        try {
            if (visitorId != null) {
                int id = Integer.parseInt(visitorId);
                assignedTrainer.setVisitor(new User(id));
            } else {
                throw new IncorrectFormDataException(AttrName.VISITOR_ID, visitorId);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(AttrName.VISITOR_ID, visitorId);
        }

        try {
            if (trainerId != null) {
                int id = Integer.parseInt(trainerId);
                assignedTrainer.setTrainer(new User(id));
            } else {
                throw new IncorrectFormDataException(AttrName.TRAINER_ID, trainerId);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectFormDataException(AttrName.TRAINER_ID, trainerId);
        }

        assignedTrainer.setBeginDate(LocalDate.now());
        return assignedTrainer;
    }
}

