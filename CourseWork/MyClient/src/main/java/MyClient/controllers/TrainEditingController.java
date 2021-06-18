package MyClient.controllers;

import MyClient.MyClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import MyClient.models.Train;

public class TrainEditingController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField numberOfPlacesField;
    private MyClient main;

    private Stage dialogStage;
    private Train train;
    private boolean clicked = false;

    public void setMain(MyClient main){
        this.main = main;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setTrain(Train train) {
        this.train = train;
        nameField.setText(train.getName());
        typeField.setText(train.getType());
        try {
            numberOfPlacesField.setText(Integer.toString(train.getNumberOfPlaces()));
        } catch (NullPointerException e){
            numberOfPlacesField.setText("");
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isDataValid() {
        String outMessage = "";
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            outMessage += "No valid name!\n";
        }
        if (typeField.getText() == null || typeField.getText().length() == 0) {
            outMessage += "No valid type!\n";
        }
        if (numberOfPlacesField.getText() == null || numberOfPlacesField.getText().length() == 0) {
            outMessage += "No valid number of places!\n";
        }
        else{
            try {
                Integer.parseInt(numberOfPlacesField.getText());
            } catch (NumberFormatException e) {
                outMessage += "number of places must be an integer!\n";
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
    private void handleOk(){
        if(isDataValid()){

            if (train.getTrainId() == null){
                train.setTrainId(main.generateId("train"));
            }
            train.setName(nameField.getText());
            train.setType(typeField.getText());
            train.setNumberOfPlaces(Integer.parseInt(numberOfPlacesField.getText()));
            clicked = true;
            dialogStage.close();
        }

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
