package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.trainer.AddAssignedComplexCommand;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.FilePath;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class AddExerciseCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(AddExerciseCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Exercise> validator = new ExerciseValidator();
            Exercise exercise = validator.validate(request);

            //picture
            Part filePart = request.getPart("picture"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            InputStream fileContent = filePart.getInputStream();
            //копируем файл
            String path1 = request.getServletContext().getRealPath("img/exercises");
            Path path = Paths.get(path1, fileName);
            Files.copy(fileContent, path, StandardCopyOption.REPLACE_EXISTING);
            exercise.setPicturePath("/img/exercises/" + fileName);

            exercise.setAudioPath("/audio/");
            ExerciseService exerciseService = factory.getService(ExerciseService.class);
            exerciseService.save(exercise);
            return new RedirectState("exercise/list.html");
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MESSAGE, "You have entered incorrect data: " + e.getMessage());
            return new ForwardState("exercise/add.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ErrorState();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ErrorState();
    }

}

