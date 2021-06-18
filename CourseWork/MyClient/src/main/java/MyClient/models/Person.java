package MyClient.models;

import javafx.beans.property.*;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Person {
    private SimpleObjectProperty<Long> personId;
    private StringProperty firstName;
    private StringProperty lastName;
    private ObjectProperty<LocalDate> birthDate;
    private SimpleObjectProperty<Long> phoneNumber;
    public Person(){
        this(null, null, null, null, null);
    }

    public Person(Long personId, String firstName, String lastName, LocalDate birthDate, Long phoneNumber){
        this.personId = new SimpleObjectProperty<>(personId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.birthDate = new SimpleObjectProperty<>(birthDate);
        this.phoneNumber = new SimpleObjectProperty<>(phoneNumber);
    }

    public Long getPersonId() {
        return personId.get();
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public LocalDate getBirthDate() {
        return birthDate.get();
    }
    public Long getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPersonId(Long personId) {
        this.personId.set(personId);
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }
    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("personId", this.getPersonId());
        json.put("firstName", this.getFirstName());
        json.put("lastName", this.getLastName());
        json.put("birthDate", this.getBirthDate().toString());
        json.put("phoneNumber", this.getPhoneNumber());

        return json;
    }
}
