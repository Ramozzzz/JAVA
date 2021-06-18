package MyClient;

import MyClient.controllers.*;
import MyClient.models.Person;
import MyClient.models.Ticket;
import MyClient.models.Train;
import MyClient.models.Route;
import MyClient.utils.DateUtil;
import MyClient.utils.TimeUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MyClient extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private final ObservableList<Person> personData = FXCollections.observableArrayList();
    private final ObservableList<Route> routeData = FXCollections.observableArrayList();
    private final ObservableList<Ticket> ticketData = FXCollections.observableArrayList();
    private final ObservableList<Train> trainData = FXCollections.observableArrayList();

    private Long maxPersonId = 0L;
    private Long maxRouteId = 0L;
    private Long maxTicketId = 0L;
    private Long maxTrainId = 0L;

    public ObservableList<Person> getPersonData(){
        return personData;
    }
    public ObservableList<Route> getRouteData(){
        return routeData;
    }
    public ObservableList<Ticket> getTicketData(){
        return ticketData;
    }
    public ObservableList<Train> getTrainData(){
        return trainData;
    }

    public Long idToSearch;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = new OkHttpClient();
    public String restServerPath = "http://localhost:8080";

    public Long generateId(String entityName){
        Long idToReturn = 0L;

        switch (entityName) {
            case "person" -> {
                if (maxPersonId.equals(0L)) {
                    for (Person person : personData) {
                        Long personId = person.getPersonId();
                        if (maxPersonId < personId) {
                            maxPersonId = personId;
                        }
                    }
                }
                maxPersonId += 1;
                idToReturn = maxPersonId;
            }
            case "route" -> {
                if (maxRouteId.equals(0L)) {
                    for (Route route : routeData) {
                        Long routeId = route.getRouteId();
                        if (maxRouteId < routeId) {
                            maxRouteId = routeId;
                        }
                    }
                }
                maxRouteId += 1;
                idToReturn = maxRouteId;
            }
            case "ticket" -> {
                if (maxTicketId.equals(0L)) {
                    for (Ticket ticket : ticketData) {
                        Long ticketId = ticket.getTicketId();
                        if (maxTicketId < ticketId) {
                            maxTicketId = ticketId;
                        }
                    }
                }
                maxTicketId += 1;
                idToReturn = maxTicketId;
            }
            case "train" -> {
                if (maxTrainId.equals(0L)) {
                    for (Train train : trainData) {
                        Long trainId = train.getTrainId();
                        if (maxTrainId < trainId) {
                            maxTrainId = trainId;
                        }
                    }
                }
                maxTrainId += 1;
                idToReturn = maxTrainId;
            }
        }

        return idToReturn;
    }

    public boolean idExists(String entityName, Long id){
        switch (entityName) {
            case "person":
                for (Person person : personData) {
                    if (person.getPersonId().equals(id)) {
                        return true;
                    }
                }
                break;
            case "route":
                for (Route route : routeData) {
                    if (route.getRouteId().equals(id)) {
                        return true;
                    }
                }
                break;
            case "train":
                for (Train train : trainData) {
                    if (train.getTrainId().equals(id)) {
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    public Person getPersonFromJson(JSONObject personJsonObject){
        Long personId = (Long) personJsonObject.get("personId");
        String firstName = (String) personJsonObject.get("firstName");
        String lastName = (String) personJsonObject.get("lastName");
        LocalDate birthDate = DateUtil.parse((String) personJsonObject.get("birthDate"));
        Long phoneNumber = (Long) personJsonObject.get("phoneNumber");
        Person person = new Person(personId, firstName, lastName, birthDate, phoneNumber);

        return person;
    }

    public Train getTrainFromJson(JSONObject trainJsonObject){
        Long trainId = (Long) trainJsonObject.get("trainId");
        String name = (String) trainJsonObject.get("name");
        String type = (String) trainJsonObject.get("type");
        Integer numberOfPlaces = Integer.parseInt(Long.toString((Long) trainJsonObject.get("numberOfPlaces")));
        Train train = new Train(trainId, name, type, numberOfPlaces);

        return train;
    }

    public Route getRouteFromJson(JSONObject routeJsonObject){
        Long routeId = (Long) routeJsonObject.get("routeId");
        LocalDate routeDate = DateUtil.parse((String) routeJsonObject.get("routeDate"));
        String departurePoint = (String) routeJsonObject.get("departurePoint");
        String destination = (String) routeJsonObject.get("destination");
        LocalTime departureTime = TimeUtil.parse((String) routeJsonObject.get("departureTime"));
        LocalTime arrivalTime = TimeUtil.parse((String) routeJsonObject.get("arrivalTime"));
        JSONObject trainJsonObject = (JSONObject) routeJsonObject.get("train");
        Train train = getTrainFromJson(trainJsonObject);
        Route route = new Route(routeId, routeDate, departurePoint, destination, departureTime, arrivalTime, train);

        return route;
    }

    public Ticket getTicketFromJson(JSONObject ticketJsonObject){
        Long ticketId = (Long) ticketJsonObject.get("ticketId");
        LocalDate contractDate = DateUtil.parse((String) ticketJsonObject.get("contractDate"));
        Integer sittingPlace = Integer.parseInt(Long.toString((Long) ticketJsonObject.get("sittingPlace")));
        Integer price = Integer.parseInt(Long.toString((Long) ticketJsonObject.get("price")));
        JSONObject personJsonObject = (JSONObject) ticketJsonObject.get("person");
        Person person = getPersonFromJson(personJsonObject);
        JSONObject routeJsonObject = (JSONObject) ticketJsonObject.get("route");
        Route route = getRouteFromJson(routeJsonObject);
        Ticket ticket = new Ticket(ticketId, contractDate, sittingPlace, price, person, route);

        return ticket;
    }

    public void fillData() throws IOException, ParseException {
        MyClient myClient = new MyClient();

        String targetPath = "/person";
        String response = myClient.getRequest(restServerPath + targetPath);
        JSONArray personJsonArray = myClient.stringToJSONArray(response);

        for (Object personObject: personJsonArray){
            JSONObject personJsonObject = (JSONObject) personObject;
            Person person = getPersonFromJson(personJsonObject);
            personData.add(person);
        }

        targetPath = "/train";
        response = myClient.getRequest(restServerPath + targetPath);
        JSONArray trainJsonArray = myClient.stringToJSONArray(response);

        for (Object trainObject: trainJsonArray){
            JSONObject trainJsonObject = (JSONObject) trainObject;
            Train train = getTrainFromJson(trainJsonObject);
            trainData.add(train);
        }

        targetPath = "/route";
        response = myClient.getRequest(restServerPath + targetPath);
        JSONArray routeJsonArray = myClient.stringToJSONArray(response);

        for (Object routeObject: routeJsonArray){
            JSONObject routeJsonObject = (JSONObject) routeObject;
            Route route = getRouteFromJson(routeJsonObject);
            routeData.add(route);
        }

        targetPath = "/ticket";
        response = myClient.getRequest(restServerPath + targetPath);
        JSONArray ticketJsonArray = myClient.stringToJSONArray(response);

        for (Object ticketObject: ticketJsonArray){
            JSONObject ticketJsonObject = (JSONObject) ticketObject;
            Ticket ticket = getTicketFromJson(ticketJsonObject);
            ticketData.add(ticket);
        }
    }

    //HTTP request functions
    public String getRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String postRequest(String url, String jsonString) throws IOException {
        RequestBody body = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String putRequest(String url, String jsonString) throws IOException {
        RequestBody body = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder().url(url).put(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String deleteRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).delete().build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public JSONObject stringToJSON(String jsonString) throws ParseException {
        Object object = new JSONParser().parse(jsonString);
        JSONObject jsonObject = (JSONObject) object;

        return jsonObject;
    }

    public JSONArray stringToJSONArray(String jsonArrayString) throws ParseException {
        Object object = new JSONParser().parse(jsonArrayString);
        JSONArray jsonArray = (JSONArray) object;

        return jsonArray;
    }

    //JavaFX methods
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("My Application");

        fillData();
        initRootLayout();
        showPersonOverview();
    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/root.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean showFindDialog(String entityName){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/findDialog.fxml"));

            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("FIND " + entityName.toUpperCase());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            FindDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setArgs(this, entityName);
            dialogStage.showAndWait();
            return controller.isClicked();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showPersonOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/personOverview.fxml"));
            AnchorPane personOverview = loader.load();
            rootLayout.setCenter(personOverview);

            PersonOverviewController controller = loader.getController();
            controller.setMain(this);


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean showPersonEditDialog(Person person, String mode){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/personEditing.fxml"));

            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(mode);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PersonEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            controller.setMain(this);
            dialogStage.showAndWait();
            return controller.isClicked();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showRouteOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/routeOverview.fxml"));
            AnchorPane routeOverview = loader.load();
            rootLayout.setCenter(routeOverview);

            RouteOverviewController controller = loader.getController();
            controller.setMain(this);


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean showRouteEditDialog(Route route, String mode){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/routeEditing.fxml"));

            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(mode);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            RouteEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRoute(route);
            controller.setMain(this);
            dialogStage.showAndWait();
            return controller.isClicked();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showTicketOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/ticketOverview.fxml"));
            AnchorPane ticketOverview = loader.load();
            rootLayout.setCenter(ticketOverview);

            TicketOverviewController controller = loader.getController();
            controller.setMain(this);


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean showTicketEditDialog(Ticket ticket, String mode){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/ticketEditing.fxml"));

            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(mode);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            TicketEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTicket(ticket);
            controller.setMain(this);
            dialogStage.showAndWait();
            return controller.isClicked();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showTrainOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/trainOverview.fxml"));
            AnchorPane trainOverview = loader.load();
            rootLayout.setCenter(trainOverview);

            TrainOverviewController controller = loader.getController();
            controller.setMain(this);


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean showTrainEditDialog(Train train, String mode){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyClient.class.getResource("views/trainEditing.fxml"));

            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(mode);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            TrainEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTrain(train);
            controller.setMain(this);
            dialogStage.showAndWait();
            return controller.isClicked();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) throws IOException, ParseException { launch(args); }
}
