package MyClient.controllers;

import MyClient.MyClient;
import MyClient.models.Person;
import MyClient.models.Route;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import MyClient.models.Ticket;
import MyClient.utils.DateUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class TicketEditingController {
    @FXML
    private TextField contractDateField;
    @FXML
    private TextField sittingPlaceField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField personIdField;
    @FXML
    private TextField routeIdField;
    private MyClient main;

    private Stage dialogStage;
    private Ticket ticket;
    private boolean clicked = false;

    public void setMain(MyClient main){
        this.main = main;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        contractDateField.setText(DateUtil.format(ticket.getContractDate()));
        contractDateField.setPromptText("yyyy-MM-dd");
        try {
            sittingPlaceField.setText(Integer.toString(ticket.getSittingPlace()));
        } catch (NullPointerException e){
            sittingPlaceField.setText("");
        }
        try {
            priceField.setText(Integer.toString(ticket.getPrice()));
        } catch (NullPointerException e){
            priceField.setText("");
        }
        try {
            personIdField.setText(Long.toString(ticket.getPerson().getPersonId()));
        } catch (NullPointerException e){
            personIdField.setText("");
        }
        try {
            routeIdField.setText(Long.toString(ticket.getRoute().getRouteId()));
        } catch (NullPointerException e){
            routeIdField.setText("");
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isDataValid() {
        String outMessage = "";
        if (contractDateField.getText() == null || contractDateField.getText().length() == 0) {
            outMessage += "No valid contract date!\n";
        } else {
            if (!DateUtil.validDate(contractDateField.getText())) {
                outMessage += "Use the format yyyy-MM-dd for datetime!\n";
            }
        }
        if (sittingPlaceField.getText() == null || sittingPlaceField.getText().length() == 0) {
            outMessage += "No valid sitting place!\n";
        }
        else{
            try {
                Integer.parseInt(sittingPlaceField.getText());
            } catch (NumberFormatException e) {
                outMessage += "sitting place must be an integer!\n";
            }
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            outMessage += "No valid price!\n";
        }
        else{
            try {
                Integer.parseInt(priceField.getText());
            } catch (NumberFormatException e) {
                outMessage += "price must be an integer!\n";
            }
        }
        if (personIdField.getText() == null || personIdField.getText().length() == 0) {
            outMessage += "No valid person id!\n";
        }
        else{
            try {
                Long personId = Long.parseLong(personIdField.getText());
                if (!main.idExists("person", personId))
                    outMessage += "can't find a person with id " + personId + "\n";
            } catch (NumberFormatException e) {
                outMessage += "person id must be a long!\n";
            }
        }
        if (routeIdField.getText() == null || routeIdField.getText().length() == 0) {
            outMessage += "No valid route id!\n";
        }
        else{
            try {
                Long routeId = Long.parseLong(routeIdField.getText());
                if (!main.idExists("route", routeId))
                    outMessage += "can't find a route with id " + routeId + "\n";
            } catch (NumberFormatException e) {
                outMessage += "route id must be a long!\n";
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

            if (ticket.getTicketId() == null){
                ticket.setTicketId(main.generateId("ticket"));
            }
            ticket.setContractDate(DateUtil.parse(contractDateField.getText()));
            ticket.setSittingPlace(Integer.parseInt(sittingPlaceField.getText()));
            ticket.setPrice(Integer.parseInt(priceField.getText()));

            String targetPath = "/person/" + Long.parseLong(personIdField.getText());
            JSONObject personJsonObject = main.stringToJSON(main.getRequest(main.restServerPath + targetPath));
            Person person = main.getPersonFromJson(personJsonObject);
            ticket.setPerson(person);

            targetPath = "/route/" + Long.parseLong(routeIdField.getText());
            JSONObject routeJsonObject = main.stringToJSON(main.getRequest(main.restServerPath + targetPath));
            Route route = main.getRouteFromJson(routeJsonObject);
            ticket.setRoute(route);

            clicked = true;
            dialogStage.close();
        }

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
