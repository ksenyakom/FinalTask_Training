package by.ksu.training.service.entity;

import by.ksu.training.dao.AssignedTrainerDao;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class AssignedTrainerServiceImpl extends ServiceImpl implements AssignedTrainerService {
    @Override
    public List<AssignedTrainer> findAll() throws PersistentException {
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);
        return atDao.read();
    }

    @Override
    public AssignedTrainer findByIdentity(Integer id) throws PersistentException {
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);
        return atDao.read(id);
    }

    @Override
    public void save(AssignedTrainer assignedTrainer) throws PersistentException {
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);
        if (assignedTrainer.getId() != null) {
            atDao.update(assignedTrainer);
        } else {
           // check if there is assigned trainer for this visitor
            AssignedTrainer existAssignment = atDao.readCurrentByVisitor(assignedTrainer.getVisitor());
            if (existAssignment != null) {
                existAssignment.setEndDate(LocalDate.now());
                atDao.update(existAssignment);
            }
            atDao.create(assignedTrainer);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);
        atDao.delete(id);
    }
}
