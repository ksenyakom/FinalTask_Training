package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class ShowEditComplexPageCommand extends AdminAndTrainerCommand{
    private static Logger logger = LogManager.getLogger(ShowEditComplexPageCommand.class);
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<Complex> complexValidator = new ComplexValidator();
        ComplexService complexService = factory.getService(ComplexService.class);

        Integer id = complexValidator.validateId(request);
        Complex complex = complexService.findById(id);
        request.setAttribute(AttrName.COMPLEX, complex);
        return new ForwardState("complex/edit.jsp");
    }
}
