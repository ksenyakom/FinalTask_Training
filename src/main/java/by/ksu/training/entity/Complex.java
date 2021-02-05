package by.ksu.training.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Complex extends Entity {
    private String title;
    private User trainerDeveloped;
    private User visitorFor;
    private float rating;
    private List<ComplexUnit> listOfUnits;

    /** Constructor*/
    public Complex() {}

    public Complex(Integer id) {
        this.setId(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getTrainerDeveloped() {
        return trainerDeveloped;
    }

    public void setTrainerDeveloped(User trainerDeveloped) {
        this.trainerDeveloped = trainerDeveloped;
    }

    public User getVisitorFor() {
        return visitorFor;
    }

    public void setVisitorFor(User visitorFor) {
        this.visitorFor = visitorFor;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    //TODO сделать итератор, возвращать группу следующих упражнений
    public ComplexUnit getComplexUnit(int i) {
        return listOfUnits.get(i);
    }

    public List<ComplexUnit> getListOfUnits() {
        return listOfUnits;
    }

    public void setComplexUnit(ComplexUnit unit, int i) {
        listOfUnits.set(i, unit);
    }

    public void addComplexUnit(ComplexUnit unit) {
        if (listOfUnits == null) {
            listOfUnits = new ArrayList<>();
        }
        listOfUnits.add(unit);
    }

    public void deleteComplexUnit(int i) {
        listOfUnits.remove(i);
    }

    public void setListOfComplexUnit(List<ComplexUnit> list) {
        if (listOfUnits == null) {
            listOfUnits = new ArrayList<>(list);
        } else {
            listOfUnits.clear();
            listOfUnits.addAll(list);
        }
    }

    public int sizeOfComplexUnitList() {
        if (listOfUnits == null) {
            return 0;
        } else {
            return listOfUnits.size();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Complex complex = (Complex) o;
        return Objects.equals(title, complex.title) &&
                Objects.equals(trainerDeveloped, complex.trainerDeveloped) &&
                Objects.equals(visitorFor, complex.visitorFor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, trainerDeveloped, visitorFor);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        if (listOfUnits != null && listOfUnits.size() != 0) {
            listOfUnits.forEach(unit -> sb.append(unit.exercise.getId()).append(","));
            sb.deleteCharAt(sb.length() - 1);
        } //TODO привести в порядок
        String s = "Complex{" + super.toString();
        s += ", title=" + title;
        s += ", trainerDeveloped=" + (trainerDeveloped == null ? null : trainerDeveloped.getId());
        s += ", visitorFor=" + (visitorFor == null ? null : visitorFor.getId());
        s += ", rating=" + rating;
        s += ", listOfUnits=" + sb;
        s += "} ";
        return s;
    }

    public static class ComplexUnit {
        private Exercise exercise;
        private int repeat;
        private int group;

        public Exercise getExercise() {
            return exercise;
        }

        public void setExercise(Exercise exercise) {
            this.exercise = exercise;
        }

        public int getRepeat() {
            return repeat;
        }

        public void setRepeat(int repeat) {
            this.repeat = repeat;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComplexUnit that = (ComplexUnit) o;
            return repeat == that.repeat &&
                    group == that.group &&
                    Objects.equals(exercise, that.exercise);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exercise, repeat, group);
        }

        @Override
        public String toString() {
            return "TrainingUnit{" +
                    "exercise=" + exercise.getId() +
                    ", repeat=" + repeat +
                    ", group=" + group +
                    '}';
        }
    }


}
