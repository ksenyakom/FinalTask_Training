package by.ksu.training.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Subscription extends Entity{
    private User visitor;
    private LocalDate beginDate;
    private LocalDate endDate;
    private BigDecimal price;

    public Subscription() {
    }

    public Subscription(Integer id) {
        this.setId(id);
    }

    public User getVisitor() {
        return visitor;
    }

    public void setVisitor(User visitor) {
        this.visitor = visitor;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(visitor, that.visitor) &&
                Objects.equals(beginDate, that.beginDate) &&
                Objects.equals(endDate, that.endDate) &&
                price.compareTo(that.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), visitor, beginDate, endDate, price);
    }

    @Override
    public String toString() {
        return "Subscription{" + super.toString() +
                ", visitor=" + visitor.getId() +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", price="+ price +
                "} " ;
    }
}
