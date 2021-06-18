package MyClient.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import MyClient.MyClient;
import MyClient.models.Person;
import MyClient.utils.DateUtil;

import java.io.IOException;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> personIdColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label birthDateLabel;
    @FXML
    private Label phoneNumberLabel;
    private MyClient main;

    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {

        personIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(Long.toString(cellData.getValue().getPersonId())));

        showPersonsOverviewDetails(null);
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonsOverviewDetails(newValue)
        );

    }

    public void setMain(MyClient main) {
        this.main = main;
        personTable.setItems(main.getPersonData());

    }

    @FXML
    private void handlePrev(){ main.showTrainOverview(); }
    @FXML
    private void handleNext(){
        main.showRouteOverview();
    }

    @FXML
    private void handleFind(){
        TableView.TableViewSelectionModel<Person> personSelectionModel = personTable.getSelectionModel();
        boolean okClicked = main.showFindDialog("person");
        if (okClicked){
            Person personToSelect = null;
            for (Person person: personTable.getItems()){
                if (person.getPersonId().equals(main.idToSearch)){
                    personToSelect = person;
                }
            }
            int personIndex = personTable.getItems().indexOf(personToSelect);
            personSelectionModel.clearSelection();
            personSelectionModel.select(personIndex);
        }
    }

    @FXML
    private void handleDeletePerson() throws IOException {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Long personId = personTable.getItems().get(selectedIndex).getPersonId();
            String targetPath = "/person/" + personId;
            String response = main.deleteRequest(main.restServerPath + targetPath);
            if (response.equals("")){
                personTable.getItems().remove(selectedIndex);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Delete Error");
                alert.setHeaderText("Deletion is impossible here");
                alert.setContentText("You can't delete this person because it's still used as a foreign key in another table");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Person selection");
            alert.setContentText("Please, select person in the table");
            alert.showAndWait();
        }
    }

    private void showPersonsOverviewDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            birthDateLabel.setText(DateUtil.format(person.getBirthDate()));
            phoneNumberLabel.setText(Long.toString(person.getPhoneNumber()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            phoneNumberLabel.setText("");
            birthDateLabel.setText("");
        }
    }

    @FXML
    private void handleNewPerson() throws IOException {
        Person newPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(newPerson,"NEW PERSON");
        if (okClicked) {
            if (main.getPersonData().contains(newPerson)){
                System.out.println("ok");
            }
            String targetPath = "/person";
            main.getPersonData().add(newPerson);
            main.postRequest(main.restServerPath + targetPath, newPerson.toJson().toString());
        }
    }

    @FXML
    private void handleEditPerson() throws IOException {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson,"EDIT PERSON");
            if (okClicked) {
                showPersonsOverviewDetails(selectedPerson);
                String targetPath = "/person/" + selectedPerson.getPersonId();
                main.putRequest(main.restServerPath + targetPath, selectedPerson.toJson().toString());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }
}
