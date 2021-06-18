package MyClient.controllers;

import MyClient.MyClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindDialogController {
    @FXML
    private TextField idTextField;
    private MyClient main;
    private String entityName;

    private Stage dialogStage;
    private boolean clicked = false;

    public void setArgs(MyClient main, String entityName){
        this.main = main;
        this.entityName = entityName;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isDataValid() {
        String outMessage = "";

        if (idTextField.getText() == null || idTextField.getText().length() == 0) {
            outMessage += "No valid " + entityName + " id!\n";
        }
        else{
            try {
                Long id = Long.parseLong(idTextField.getText());
                if (!main.idExists(entityName, id))
                    outMessage += "can't find a " + entityName + " with id " + id + "\n";
            } catch (NumberFormatException e) {
                outMessage += entityName + " id must be a long!\n";
            }
        }

        if (outMessage.equals("")){
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
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

            main.idToSearch = Long.parseLong(idTextField.getText());
            clicked = true;
            dialogStage.close();
        }

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
