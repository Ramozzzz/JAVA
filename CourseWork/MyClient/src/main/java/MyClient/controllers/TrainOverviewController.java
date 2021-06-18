package MyClient.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import MyClient.MyClient;
import MyClient.models.Train;

import java.io.IOException;

public class TrainOverviewController {
    @FXML
    private TableView<Train> trainTable;
    @FXML
    private TableColumn<Train, String> trainIdColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label numberOfPlacesLabel;
    private MyClient main;

    public TrainOverviewController() {
    }

    @FXML
    private void initialize() {

        trainIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(Long.toString(cellData.getValue().getTrainId())));

        showTrainsOverviewDetails(null);
        trainTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTrainsOverviewDetails(newValue)
        );

    }

    public void setMain(MyClient main) {
        this.main = main;
        trainTable.setItems(main.getTrainData());

    }

    @FXML
    private void handlePrev(){ main.showTicketOverview(); }
    @FXML
    private void handleNext(){
        main.showPersonOverview();
    }

    @FXML
    private void handleFind(){
        TableView.TableViewSelectionModel<Train> trainSelectionModel = trainTable.getSelectionModel();
        boolean okClicked = main.showFindDialog("train");
        if (okClicked){
            Train trainToSelect = null;
            for (Train train: trainTable.getItems()){
                if (train.getTrainId().equals(main.idToSearch)){
                    trainToSelect = train;
                }
            }
            int trainIndex = trainTable.getItems().indexOf(trainToSelect);
            trainSelectionModel.clearSelection();
            trainSelectionModel.select(trainIndex);
        }
    }

    @FXML
    private void handleDeleteTrain() throws IOException {
        int selectedIndex = trainTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Long trainId = trainTable.getItems().get(selectedIndex).getTrainId();
            String targetPath = "/train/" + trainId;
            String response = main.deleteRequest(main.restServerPath + targetPath);
            if (response.equals("")){
                trainTable.getItems().remove(selectedIndex);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Delete Error");
                alert.setHeaderText("Deletion is impossible here");
                alert.setContentText("You can't delete this train because it's still used as a foreign key in another table");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No train selection");
            alert.setContentText("Please, select train in the table");
            alert.showAndWait();
        }
    }

    private void showTrainsOverviewDetails(Train train) {
        if (train != null) {
            nameLabel.setText(train.getName());
            typeLabel.setText(train.getType());
            numberOfPlacesLabel.setText(Integer.toString(train.getNumberOfPlaces()));
        } else {
            nameLabel.setText("");
            typeLabel.setText("");
            numberOfPlacesLabel.setText("");
        }
    }

    @FXML
    private void handleNewTrain() throws IOException {
        Train newTrain = new Train();
        boolean okClicked = main.showTrainEditDialog(newTrain,"NEW TRAIN");
        if (okClicked) {
            if (main.getTrainData().contains(newTrain)){
                System.out.println("ok");
            }
            String targetPath = "/train";
            main.getTrainData().add(newTrain);
            main.postRequest(main.restServerPath + targetPath, newTrain.toJson().toString());
        }
    }

    @FXML
    private void handleEditTrain() throws IOException {
        Train selectedTrain = trainTable.getSelectionModel().getSelectedItem();
        if (selectedTrain != null) {
            boolean okClicked = main.showTrainEditDialog(selectedTrain,"EDIT TRAIN");
            if (okClicked) {
                showTrainsOverviewDetails(selectedTrain);
                String targetPath = "/train/" + selectedTrain.getTrainId();
                main.putRequest(main.restServerPath + targetPath, selectedTrain.toJson().toString());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Train Selected");
            alert.setContentText("Please select a train in the table.");
            alert.showAndWait();
        }
    }
}
