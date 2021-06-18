package MyClient.models;

import javafx.beans.property.*;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;

public class Route {

    private SimpleObjectProperty<Long> routeId;
    private ObjectProperty<LocalDate> routeDate;
    private StringProperty departurePoint;
    private StringProperty destination;
    private ObjectProperty<LocalTime> departureTime;
    private ObjectProperty<LocalTime> arrivalTime;
    private ObjectProperty<Train> train;

    public Route(){
        this(null, null, null, null, null, null, null);
    }

    public Route(Long routeId, LocalDate routeDate, String departurePoint, String destination,
                 LocalTime departureTime, LocalTime arrivalTime, Train train){
        this.routeId = new SimpleObjectProperty<>(routeId);
        this.routeDate = new SimpleObjectProperty<>(routeDate);
        this.departurePoint = new SimpleStringProperty(departurePoint);
        this.destination = new SimpleStringProperty(destination);
        this.departureTime = new SimpleObjectProperty<>(departureTime);
        this.arrivalTime = new SimpleObjectProperty<>(arrivalTime);
        this.train = new SimpleObjectProperty<>(train);
    }

    public Long getRouteId() {
        return routeId.get();
    }

    public LocalDate getRouteDate() {
        return routeDate.get();
    }

    public String getDeparturePoint() {
        return departurePoint.get();
    }

    public String getDestination() {
        return destination.get();
    }

    public LocalTime getDepartureTime() {
        return departureTime.get();
    }

    public LocalTime getArrivalTime() {
        return arrivalTime.get();
    }

    public Train getTrain() {
        return train.get();
    }

    public void setRouteId(Long routeId) {
        this.routeId.set(routeId);
    }

    public void setRouteDate(LocalDate routeDate) {
        this.routeDate.set(routeDate);
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint.set(departurePoint);
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime.set(departureTime);
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public void setTrain(Train train) {
        this.train.set(train);
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("routeId", this.getRouteId());
        json.put("routeDate", this.getRouteDate().toString());
        json.put("departurePoint", this.getDeparturePoint());
        json.put("destination", this.getDestination());
        json.put("departureTime", this.getDepartureTime().toString());
        json.put("arrivalTime", this.getArrivalTime().toString());
        json.put("train", this.getTrain().toJson());

        return json;
    }
}
