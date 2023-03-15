package dimakovalenko.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;
    @NotEmpty(message = "Имя человека не должно быть пустым")
    @Size(min=2,max=100,message = "Имя человека должно быть от 2 до 100 символов")
    private String fullName;
    @Min(message = "Год рождения должен быть больше 1900", value =1900)
    private int yearOfBirth;

    public Person() {
    }
    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
