package MyClient.models;

import javafx.beans.property.*;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Ticket {

    private SimpleObjectProperty<Long> ticketId;
    private ObjectProperty<LocalDate> contractDate;
    private SimpleObjectProperty<Integer> sittingPlace;
    private SimpleObjectProperty<Integer> price;
    private ObjectProperty<Person> person;
    private ObjectProperty<Route> route;

    public Ticket(){
        this(null, null, null, null, null, null);
    }

    public Ticket(Long ticketId, LocalDate contractDate, Integer sittingPlace, Integer price, Person person,
                  Route route){
        this.ticketId = new SimpleObjectProperty<>(ticketId);
        this.contractDate = new SimpleObjectProperty<>(contractDate);
        this.sittingPlace = new SimpleObjectProperty<>(sittingPlace);
        this.price = new SimpleObjectProperty<>(price);
        this.person = new SimpleObjectProperty<>(person);
        this.route = new SimpleObjectProperty<>(route);
    }

    public Long getTicketId() {
        return ticketId.get();
    }

    public LocalDate getContractDate() {
        return contractDate.get();
    }

    public Integer getSittingPlace() {
        return sittingPlace.get();
    }

    public Integer getPrice() {
        return price.get();
    }

    public Person getPerson() {
        return person.get();
    }

    public Route getRoute() {
        return route.get();
    }

    public void setTicketId(Long ticketId) {
        this.ticketId.set(ticketId);
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate.set(contractDate);
    }

    public void setSittingPlace(Integer sittingPlace) {
        this.sittingPlace.set(sittingPlace);
    }

    public void setPrice(Integer price) {
        this.price.set(price);
    }

    public void setPerson(Person person) {
        this.person.set(person);
    }

    public void setRoute(Route route) {
        this.route.set(route);
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("ticketId", this.getTicketId());
        json.put("contractDate", this.getContractDate().toString());
        json.put("sittingPlace", this.getSittingPlace());
        json.put("price", this.getPrice());
        json.put("person", this.getPerson().toJson());
        json.put("route", this.getRoute().toJson());

        return json;
    }
}
