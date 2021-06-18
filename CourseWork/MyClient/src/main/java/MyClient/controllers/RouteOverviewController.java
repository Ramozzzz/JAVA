package MyClient.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import MyClient.MyClient;
import MyClient.models.Route;
import MyClient.utils.DateUtil;
import MyClient.utils.TimeUtil;

import java.io.IOException;

public class RouteOverviewController {
    @FXML
    private TableView<Route> routeTable;
    @FXML
    private TableColumn<Route, String> routeIdColumn;
    @FXML
    private Label routeDateLabel;
    @FXML
    private Label departurePointLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label departureTimeLabel;
    @FXML
    private Label arrivalTimeLabel;
    @FXML
    private Label trainIdLabel;
    private MyClient main;

    public RouteOverviewController() {
    }

    @FXML
    private void initialize() {

        routeIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(Long.toString(cellData.getValue().getRouteId())));

        showRoutesOverviewDetails(null);
        routeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showRoutesOverviewDetails(newValue)
        );

    }

    public void setMain(MyClient main) {
        this.main = main;
        routeTable.setItems(main.getRouteData());

    }

    @FXML
    private void handlePrev(){ main.showPersonOverview(); }
    @FXML
    private void handleNext(){
        main.showTicketOverview();
    }

    @FXML
    private void handleFind(){
        TableView.TableViewSelectionModel<Route> routeSelectionModel = routeTable.getSelectionModel();
        boolean okClicked = main.showFindDialog("route");
        if (okClicked){
            Route routeToSelect = null;
            for (Route route: routeTable.getItems()){
                if (route.getRouteId().equals(main.idToSearch)){
                    routeToSelect = route;
                }
            }
            int routeIndex = routeTable.getItems().indexOf(routeToSelect);
            routeSelectionModel.clearSelection();
            routeSelectionModel.select(routeIndex);
        }
    }

    @FXML
    private void handleDeleteRoute() throws IOException {
        int selectedIndex = routeTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Long routeId = routeTable.getItems().get(selectedIndex).getRouteId();
            String targetPath = "/route/" + routeId;
            String response = main.deleteRequest(main.restServerPath + targetPath);
            if (response.equals("")){
                routeTable.getItems().remove(selectedIndex);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Delete Error");
                alert.setHeaderText("Deletion is impossible here");
                alert.setContentText("You can't delete this route because it's still used as a foreign key in another table");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No route selection");
            alert.setContentText("Please, select route in the table");
            alert.showAndWait();
        }
    }

    private void showRoutesOverviewDetails(Route route) {
        if (route != null) {
            routeDateLabel.setText(DateUtil.format(route.getRouteDate()));
            departurePointLabel.setText(route.getDeparturePoint());
            destinationLabel.setText(route.getDestination());
            departureTimeLabel.setText(TimeUtil.format(route.getDepartureTime()));
            arrivalTimeLabel.setText(TimeUtil.format(route.getArrivalTime()));
            trainIdLabel.setText(Long.toString(route.getTrain().getTrainId()));
        } else {
            routeDateLabel.setText("");
            departurePointLabel.setText("");
            destinationLabel.setText("");
            departureTimeLabel.setText("");
            arrivalTimeLabel.setText("");
            trainIdLabel.setText("");
        }
    }

    @FXML
    private void handleNewRoute() throws IOException {
        Route newRoute = new Route();
        boolean okClicked = main.showRouteEditDialog(newRoute,"NEW ROUTE");
        if (okClicked) {
            if (main.getRouteData().contains(newRoute)){
                System.out.println("ok");
            }
            String targetPath = "/route";
            main.getRouteData().add(newRoute);
            main.postRequest(main.restServerPath + targetPath, newRoute.toJson().toString());
        }
    }

    @FXML
    private void handleEditRoute() throws IOException {
        Route selectedRoute = routeTable.getSelectionModel().getSelectedItem();
        if (selectedRoute != null) {
            boolean okClicked = main.showRouteEditDialog(selectedRoute,"EDIT ROUTE");
            if (okClicked) {
                showRoutesOverviewDetails(selectedRoute);
                String targetPath = "/route/" + selectedRoute.getRouteId();
                main.putRequest(main.restServerPath + targetPath, selectedRoute.toJson().toString());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Route Selected");
            alert.setContentText("Please select a route in the table.");
            alert.showAndWait();
        }
    }
}
