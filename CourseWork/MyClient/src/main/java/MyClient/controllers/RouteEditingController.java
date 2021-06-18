package MyClient.controllers;

import MyClient.MyClient;
import MyClient.models.Train;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import MyClient.models.Route;
import MyClient.utils.DateUtil;
import MyClient.utils.TimeUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class RouteEditingController {
    @FXML
    private TextField routeDateField;
    @FXML
    private TextField departurePointField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField departureTimeField;
    @FXML
    private TextField arrivalTimeField;
    @FXML
    private TextField trainIdField;
    private MyClient main;

    private Stage dialogStage;
    private Route route;
    private boolean clicked = false;

    public void setMain(MyClient main){
        this.main = main;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setRoute(Route route) {
        this.route = route;
        routeDateField.setText(DateUtil.format(route.getRouteDate()));
        routeDateField.setPromptText("yyyy-MM-dd");
        departurePointField.setText(route.getDeparturePoint());
        destinationField.setText(route.getDestination());
        departureTimeField.setText(TimeUtil.format(route.getDepartureTime()));
        departureTimeField.setPromptText("HH:mm:ss");
        arrivalTimeField.setText(TimeUtil.format(route.getArrivalTime()));
        arrivalTimeField.setPromptText("HH:mm:ss");
        try {
            trainIdField.setText(Long.toString(route.getTrain().getTrainId()));
        } catch (NullPointerException e){
            trainIdField.setText("");
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isDataValid(){
        String outMessage = "";
        if (routeDateField.getText() == null || routeDateField.getText().length() == 0) {
            outMessage += "No valid route date!\n";
        } else {
            if (!DateUtil.validDate(routeDateField.getText())) {
                outMessage += "Use the format yyyy-MM-dd for datetime!\n";
            }
        }
        if (departurePointField.getText() == null || departurePointField.getText().length() == 0) {
            outMessage += "No valid departure point!\n";
        }
        if (destinationField.getText() == null || destinationField.getText().length() == 0) {
            outMessage += "No valid destination!\n";
        }
        if (departureTimeField.getText() == null || departureTimeField.getText().length() == 0) {
            outMessage += "No valid departure time!\n";
        } else {
            if (!TimeUtil.validTime(departureTimeField.getText())) {
                outMessage += "Use the format HH:mm:ss for datetime!\n";
            }
        }
        if (arrivalTimeField.getText() == null || arrivalTimeField.getText().length() == 0) {
            outMessage += "No valid arrival time!\n";
        } else {
            if (!TimeUtil.validTime(arrivalTimeField.getText())) {
                outMessage += "Use the format HH:mm:ss for datetime!\n";
            }
        }
        if (trainIdField.getText() == null || trainIdField.getText().length() == 0) {
            outMessage += "No valid train id!\n";
        }
        else{
            try {
                Long trainId = Long.parseLong(trainIdField.getText());
                if (!main.idExists("train", trainId))
                    outMessage += "can't find a train with id " + trainId + "\n";
            } catch (NumberFormatException e) {
                outMessage += "train id must be a long!\n";
            }
        }

        if (outMessage.equals("")){
            return true;
        }
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Not valid fields!");
        alert.setHeaderText("Please correct invalid fields!");
        alert.setContentText(outMessage);
        alert.showAndWait();
        return false;

    }

    @FXML
    private void handleOk() throws IOException, ParseException {
        if(isDataValid()){

            if (route.getRouteId() == null){
                route.setRouteId(main.generateId("route"));
            }
            route.setRouteDate(DateUtil.parse(routeDateField.getText()));
            route.setDeparturePoint(departurePointField.getText());
            route.setDestination(destinationField.getText());
            route.setDepartureTime(TimeUtil.parse(departureTimeField.getText()));
            route.setArrivalTime(TimeUtil.parse(arrivalTimeField.getText()));

            String targetPath = "/train/" + trainIdField.getText();
            JSONObject trainJsonObject = main.stringToJSON(main.getRequest(main.restServerPath + targetPath));
            Train train = main.getTrainFromJson(trainJsonObject);
            route.setTrain(train);

            clicked = true;
            dialogStage.close();
        }

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
