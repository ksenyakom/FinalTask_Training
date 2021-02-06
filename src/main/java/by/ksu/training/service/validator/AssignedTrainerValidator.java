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
    public AssignedTrainer validate(HttpServletRequest request) {

        AssignedTrainer assignedTrainer = new AssignedTrainer();

        assignedTrainer.setBeginDate(LocalDate.now());
        return assignedTrainer;
    }
}

