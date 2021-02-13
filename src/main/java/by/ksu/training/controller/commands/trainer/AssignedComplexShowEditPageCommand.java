package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show on page "assigned сomplex edit".
 *
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class AssignedComplexShowEditPageCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(AssignedComplexShowEditPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<AssignedComplex> validator = new AssignedComplexValidator();
        AssignedComplexService service = factory.getService(AssignedComplexService.class);
        ComplexService complexService = factory.getService(ComplexService.class);

        int id = validator.validateId(request);
        AssignedComplex assignedComplex = service.findById(id);
        List<Complex> complexes = complexService.findComplexesMetaDataByUser(assignedComplex.getVisitor());

        request.setAttribute("lst", complexes);
        request.setAttribute(AttrName.ASSIGNED_COMPLEX, assignedComplex);
        return new ForwardState("assigned_complex/edit.jsp");
    }
}
