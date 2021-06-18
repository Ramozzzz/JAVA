package MyClient.controllers;

import MyClient.MyClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import MyClient.models.Person;
import MyClient.utils.DateUtil;


public class PersonEditingController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField birthDateField;
    @FXML
    private TextField phoneNumberField;
    private MyClient main;

    private Stage dialogStage;
    private Person person;
    private boolean clicked = false;

    public void setMain(MyClient main){
        this.main = main;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        birthDateField.setText(DateUtil.format(person.getBirthDate()));
        birthDateField.setPromptText("yyyy-MM-dd");
        try {
            phoneNumberField.setText(Long.toString(person.getPhoneNumber()));
        } catch (NullPointerException e){
            phoneNumberField.setText("");
        }
        phoneNumberField.setPromptText("XXXXXXXXXXX");
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isDataValid() {
        String outMessage = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            outMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            outMessage += "No valid last name!\n";
        }

        if (phoneNumberField.getText() == null || phoneNumberField.getText().length() != 11) {
            outMessage += "No valid phone number (it must be 11 symbols long)!\n";
        }
        else{
            try {
                Long.parseLong(phoneNumberField.getText());
            } catch (NumberFormatException e) {
                outMessage += "phone number must be a long!\n";
            }
        }

        if (birthDateField.getText() == null || birthDateField.getText().length() == 0) {
            outMessage += "No valid birth date!\n";
        } else {
            if (!DateUtil.validDate(birthDateField.getText())) {
                outMessage += "Use the format yyyy-MM-dd for datetime!\n";
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

            if (person.getPersonId() == null) {
                person.setPersonId(main.generateId("person"));
            }
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setBirthDate(DateUtil.parse(birthDateField.getText()));
            person.setPhoneNumber(Long.parseLong(phoneNumberField.getText()));
            clicked = true;
            dialogStage.close();
        }

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
