package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Author Kseniya Oznobishina
 * @Date 25.01.2021
 */
public class SaveExerciseCommand extends AdminCommand{
    private static Logger logger = LogManager.getLogger(ShowExerciseAddPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Exercise> validator = new ExerciseValidator();
            Exercise exercise = validator.validate(request);
            exercise.setId(validator.validateId(request));
            String oldPicturePath = request.getParameter("picturePath");

            //picture
            Part filePart = request.getPart("picture"); // Retrieves <input type="file" name="file">
            if (filePart != null) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                InputStream fileContent = filePart.getInputStream();
                //копируем файл
                String path1 = request.getServletContext().getRealPath("img/exercises");
                Path path = Paths.get(path1, fileName);
                Files.copy(fileContent, path, StandardCopyOption.REPLACE_EXISTING);
                exercise.setPicturePath("/img/exercises/" + fileName);
                String oldPath = request.getServletContext().getRealPath(oldPicturePath);
                Path deleteOld = Paths.get(oldPath);
                Files.delete(deleteOld); //TODO словить возможные ошибки и обработать корректно

            }

            exercise.setAudioPath("/audio/");//TODO
            ExerciseService exerciseService = factory.getService(ExerciseService.class);
            exerciseService.save(exercise);
            request.setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.updated");
            return new RedirectState("exercise/list.html");
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MESSAGE, "You have entered incorrect data: " + e.getMessage());
            return new ForwardState("exercise/add.jsp");
        } catch (PersistentException | IOException | ServletException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }

}
