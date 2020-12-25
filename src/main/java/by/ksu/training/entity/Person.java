package by.ksu.training.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Person extends Entity {
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;
    private String achievements;

    public Person() {}

    public Person(Person person) {
        setId(person.getId());
        setName(person.getName());
        setPatronymic(person.getPatronymic());
        setSurname(person.getSurname());
        setAddress(person.getAddress());
        setEmail(person.getEmail());
        setDateOfBirth(person.getDateOfBirth());
        setPhone(person.getPhone());
    }
    public Person(Integer id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(patronymic, person.patronymic) &&
                Objects.equals(dateOfBirth, person.dateOfBirth) &&
                Objects.equals(address, person.address) &&
                Objects.equals(phone, person.phone) &&
                Objects.equals(email, person.email) &&
                Objects.equals(achievements, person.achievements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, patronymic, dateOfBirth, address, phone, email, achievements);
    }

    @Override
    public String toString() {
        String s =  "Person{"  + super.toString();
               s+= ", name=" + name ;
               s+= ", surname=" + surname ;
               s+= ", patronymic=" + patronymic ;
              s+=  ", dateOfBirth=" + dateOfBirth ;
              s+=  ", address=" + address ;
              s+=  ", phone=" + phone ;
              s+=  ", email=" + email ;
              s+=  "} ";
                return s;
//        return "Person{"  + super.toString()+
//                "name=" + name +
//                ", surname=" + surname +
//                ", patronymic=" + patronymic +
//                ", dateOfBirth=" + dateOfBirth +
//                ", address=" + address +
//                ", phone=" + phone +
//                ", email=" + email +
//                "} ";
    }
}
