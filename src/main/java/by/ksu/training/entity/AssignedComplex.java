package by.ksu.training.entity;

import java.time.LocalDate;
import java.util.Objects;

public class AssignedComplex extends Entity {
    private Visitor visitor;
    private Complex complex;
    private LocalDate dateExpected;
    private LocalDate dateExecuted;

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    public LocalDate getDateExpected() {
        return dateExpected;
    }

    public void setDateExpected(LocalDate dateExpected) {
        this.dateExpected = dateExpected;
    }

    public LocalDate getDateExecuted() {
        return dateExecuted;
    }

    public void setDateExecuted(LocalDate dateExecuted) {
        this.dateExecuted = dateExecuted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AssignedComplex that = (AssignedComplex) o;
        return Objects.equals(visitor, that.visitor) &&
                Objects.equals(complex, that.complex) &&
                Objects.equals(dateExpected, that.dateExpected) &&
                Objects.equals(dateExecuted, that.dateExecuted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), visitor, complex, dateExpected, dateExecuted);
    }

    @Override
    public String toString() {
        return "AssignedComplex{" + super.toString()+
                ", visitor=" + visitor.getId() +
                ", complex=" + complex.getId() +
                ", dateExpected=" + dateExpected +
                ", dateExecuted=" + dateExecuted +
                "} ";
    }
}
