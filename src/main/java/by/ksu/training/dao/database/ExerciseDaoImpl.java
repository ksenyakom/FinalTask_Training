package by.ksu.training.dao.database;

import by.ksu.training.exception.PersistentException;
import by.ksu.training.dao.ExerciseDao;
import by.ksu.training.entity.Exercise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDaoImpl extends BaseDaoImpl implements ExerciseDao {
    private static Logger logger = LogManager.getLogger(ExerciseDaoImpl.class);

    private static String CREATE =
            "INSERT INTO `exercise`(`title`,`adjusting`,`mistakes`,`picture_path`,`audio_path`,`type_id`) VALUES(?,?,?,?,?,(select `id` from `exercise_type` where `type` = ?))";
    //    private static  String READ_BY_ID = "SELECT * FROM `exercise` WHERE `id`= ?";
    private static String READ_BY_ID = "SELECT `title`,`adjusting`,`mistakes`,`picture_path`,`audio_path`, `type` FROM `exercise` `e` join `exercise_type` `t` on e.type_id = t.id  WHERE e.id= ?";
    private static String READ_All = "SELECT e.id,`title`,`adjusting`,`mistakes`,`picture_path`,`audio_path`, `type` FROM `exercise` `e` join `exercise_type` `t` on e.type_id = t.id  ORDER BY `title`";
    private static String READ_EXERCISE_TYPES = "SELECT `type` FROM `exercise_type` ORDER BY `id`";
    private static String UPDATE =
            "UPDATE `exercise` SET `title`=?,`adjusting`=?,`mistakes`=?,`picture_path`=?,`audio_path`=?,`type_id`=(select `id` from `exercise_type` where `type` = ?) WHERE `id` = ?";
    private static String DELETE = "DELETE FROM `exercise` WHERE `id`= ?";


    @Override
    public List<Exercise> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_All)) {
            ResultSet resultSet = statement.executeQuery();
            Exercise exercise = null;
            List<Exercise> list = new ArrayList<>();

            while (resultSet.next()) {
                exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setAdjusting(resultSet.getString("adjusting"));
                exercise.setMistakes(resultSet.getString("mistakes"));
                exercise.setPicturePath(resultSet.getString("picture_path"));
                exercise.setAudioPath(resultSet.getString("audio_path"));
                exercise.setType(resultSet.getString("type"));
                list.add(exercise);
            }
            return list;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void readByExercise(List<Exercise> exercises) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            for (Exercise exercise : exercises) {
                statement.setInt(1, exercise.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        exercise.setTitle(resultSet.getString("title"));
                        exercise.setAdjusting(resultSet.getString("adjusting"));
                        exercise.setMistakes(resultSet.getString("mistakes"));
                        exercise.setPicturePath(resultSet.getString("picture_path"));
                        exercise.setAudioPath(resultSet.getString("audio_path"));
                        exercise.setType(resultSet.getString("type"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<String> readExerciseTypes() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_EXERCISE_TYPES)) {
            ResultSet resultSet = statement.executeQuery();
            List<String> list = new ArrayList<>();

            while (resultSet.next()) {
                list.add(resultSet.getString("type"));
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Integer create(Exercise entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getAdjusting());
            statement.setString(3, entity.getMistakes());
            statement.setString(4, entity.getPicturePath());
            statement.setString(5, entity.getAudioPath());
            statement.setString(6, entity.getType());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            logger.debug("inserted exercise {}", entity);
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                logger.debug("id = {}", id);
                return id;
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `readers`");
                throw new PersistentException();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Exercise read(Integer id) throws PersistentException {

        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Exercise exercise = null;

            if (resultSet.next()) {
                exercise = new Exercise();
                exercise.setId(id);
                exercise.setTitle(resultSet.getString("title"));
                exercise.setAdjusting(resultSet.getString("adjusting"));
                exercise.setMistakes(resultSet.getString("mistakes"));
                exercise.setPicturePath(resultSet.getString("picture_path"));
                exercise.setAudioPath(resultSet.getString("audio_path"));
                exercise.setType(resultSet.getString("type"));
            }
            return exercise;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Exercise entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getAdjusting());
            statement.setString(3, entity.getMistakes());
            statement.setString(4, entity.getPicturePath());
            statement.setString(5, entity.getAudioPath());
            statement.setString(6, entity.getType());
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("deleted exercise {}", id);

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
