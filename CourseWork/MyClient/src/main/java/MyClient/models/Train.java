package MyClient.models;

import javafx.beans.property.*;
import org.json.simple.JSONObject;

public class Train {

    private SimpleObjectProperty<Long> trainId;
    private StringProperty name;
    private StringProperty type;
    private SimpleObjectProperty<Integer> numberOfPlaces;

    public Train(){
        this(null, null, null, null);
    }

    public Train(Long trainId, String name, String type, Integer numberOfPlaces){
        this.trainId = new SimpleObjectProperty<>(trainId);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.numberOfPlaces = new SimpleObjectProperty<>(numberOfPlaces);
    }

    public Long getTrainId() {
        return trainId.get();
    }

    public String getName() {
        return name.get();
    }

    public String getType() {
        return type.get();
    }

    public Integer getNumberOfPlaces() {
        return numberOfPlaces.get();
    }

    public void setTrainId(Long trainId) {
        this.trainId.set(trainId);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setNumberOfPlaces(Integer numberOfPlaces) {
        this.numberOfPlaces.set(numberOfPlaces);
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("trainId", this.getTrainId());
        json.put("name", this.getName());
        json.put("type", this.getType());
        json.put("numberOfPlaces", this.getNumberOfPlaces());

        return json;
    }
}
