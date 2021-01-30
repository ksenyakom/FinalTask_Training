package by.ksu.training.service.validator;

import by.ksu.training.exception.FileTooBigException;
import by.ksu.training.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 29.01.2021
 */
public class ExerciseFileValidator implements FileValidator {
    private static Logger logger = LogManager.getLogger(ExerciseFileValidator.class);

    @Override
    public List<String> validateNew(HttpServletRequest request) throws PersistentException, FileTooBigException {
        String picturePath = null;
        String audioPath = "/audio/Dhiya_oum_ru.mp3"; // for now ((
        try {
            //picture
            Part filePart = request.getPart("picture"); // Retrieves <input type="file" name="picture">
            String fileName = filePart != null ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : ""; // MSIE fix.
            if (!fileName.isEmpty()) {
                InputStream fileContent = filePart.getInputStream();
                //copy file
                String path1 = request.getServletContext().getRealPath("img/exercises");
                Path path = Paths.get(path1, fileName);
                Files.copy(fileContent, path, StandardCopyOption.REPLACE_EXISTING);
                picturePath = "/img/exercises/" + fileName;

                return List.of(picturePath, audioPath);
            } else {
                return List.of();
            }

        } catch (ServletException e) {
            throw new PersistentException("Request is not of type multipart/form-data", e);
        } catch (IOException e) {
            throw new PersistentException("Exception while file loading", e);
        } catch (IllegalStateException e) {
            throw new FileTooBigException("File is larger then max size", e);//TODO указать макс размер
        }
    }

    @Override
    public List<String> validateUpdate(HttpServletRequest request) throws PersistentException, FileTooBigException {
        String picturePath = null;
        String audioPath = "/audio/Dhiya_oum_ru.mp3"; // for now ((
        String oldPicturePath = request.getParameter("picturePath");

        try {
            //picture
            Part filePart = request.getPart("picture"); // Retrieves <input type="file" name="picture">
            String fileName = filePart != null ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : ""; // MSIE fix.
            if (!fileName.isEmpty()) {
                InputStream fileContent = filePart.getInputStream();
                //copy file
                String path1 = request.getServletContext().getRealPath("img/exercises");
                Path path = Paths.get(path1, fileName);
                Files.copy(fileContent, path, StandardCopyOption.REPLACE_EXISTING);
                picturePath = "/img/exercises/" + fileName;

                String oldPath = request.getServletContext().getRealPath(oldPicturePath);
                Path deleteOld = Paths.get(oldPath);
                if (Files.exists(deleteOld) && !path.equals(deleteOld)) {
                    Files.delete(deleteOld);
                }
            } else {
                picturePath = oldPicturePath;
            }

            if (picturePath != null && !picturePath.isEmpty()) {
                return List.of(picturePath, audioPath);
            } else {
                return List.of();
            }

        } catch (ServletException e) {
            throw new PersistentException("Request is not of type multipart/form-data", e);
        } catch (IOException e) {
            throw new PersistentException("Exception while file loading", e);
        } catch (IllegalStateException e) {
            throw new FileTooBigException("File is larger then max size", e);//TODO указать макс размер
        }
    }
}

