package by.ksu.training.entity;

import java.time.LocalDate;
import java.util.Objects;

public class AssignedTrainer extends Entity{
    private Visitor visitor;
    private Trainer trainer;
    private LocalDate beginDate;
    private LocalDate endDate;

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AssignedTrainer that = (AssignedTrainer) o;
        return Objects.equals(visitor, that.visitor) &&
                Objects.equals(trainer, that.trainer) &&
                Objects.equals(beginDate, that.beginDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),visitor, trainer, beginDate, endDate);
    }

    @Override
    public String toString() {
        return "AssignedTrainer{" +
                super.toString() +
                ", visitor=" + (visitor == null ? null : visitor.getId()) +
                ", assignedTrainer=" + (trainer == null ? null : trainer.getId()) +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                '}';
    }
}
