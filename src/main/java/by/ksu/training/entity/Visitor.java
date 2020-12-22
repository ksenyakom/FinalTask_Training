package by.ksu.training.entity;

import java.util.List;
import java.util.Objects;

public class Visitor extends Person {
    private Trainer trainer;
    private List<AssignedComplex> complexList;

    public Visitor() {}

    public Visitor(Person person) {
        super(person);
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<AssignedComplex> getComplexList() {
        return complexList;
    }

    public void setComplexList(List<AssignedComplex> complexList) {
        this.complexList = complexList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Visitor visitor = (Visitor) o;
        return  Objects.equals(trainer, visitor.trainer) &&
                Objects.equals(complexList, visitor.complexList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trainer, complexList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        if (complexList != null) {
            complexList.forEach(assignedComplex -> sb.append(assignedComplex.getId())
                    .append("-")
                    .append(assignedComplex.getDateExpected())
                    .append(","));
        }
        sb.deleteCharAt(sb.length()-1);
        return " Visitor{" + super.toString() +
                ", achievements=" + (getAchievements()==null? "don't share" : "share") +
                ", trainer=" + (trainer==null? null: trainer.getId()) +
                ", trainingList=" + sb +
                '}';
    }
}
