package by.ksu.training.service.validator;

import by.ksu.training.entity.AssignedTrainer;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class AssignedTrainerValidator extends BaseValidator<AssignedTrainer> implements EntityValidator<AssignedTrainer> {

    @Override
    public Integer validateId(HttpServletRequest request)  {
        return null;
    }

    @Override
    public Map<String, String> getWarningMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AssignedTrainer getInvalid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AssignedTrainer validate(HttpServletRequest request) {

        AssignedTrainer assignedTrainer = new AssignedTrainer();

        assignedTrainer.setBeginDate(LocalDate.now());
        return assignedTrainer;
    }
}

