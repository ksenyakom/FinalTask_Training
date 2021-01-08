package by.ksu.training.entity;

import java.util.List;
import java.util.Objects;

public class Trainer extends Person {
    private List<Visitor> visitorList;

    public Trainer() {
    }

    public Trainer(Person person) {
        super(person);
    }

    public Trainer(Integer id) {
        super(id);
    }

    public List<Visitor> getVisitorList() {
        return visitorList;
    }

    public void setVisitorList(List<Visitor> visitorList) {
        this.visitorList = visitorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(visitorList, trainer.visitorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), visitorList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (visitorList != null) {
            visitorList.forEach(visitor -> sb.append(visitor.getId()).append(","));
        }
        sb.deleteCharAt(sb.length() - 1);
        return " Trainer{" + super.toString() +
                ", achievements=" + getAchievements() +
                ",\n visitorList=" + sb +
                '}';
    }
}
