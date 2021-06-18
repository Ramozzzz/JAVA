package MyClient.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import MyClient.MyClient;
import MyClient.models.Ticket;
import MyClient.utils.DateUtil;

import java.io.IOException;


public class TicketOverviewController {
    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, String> ticketIdColumn;
    @FXML
    private Label contractDateLabel;
    @FXML
    private Label sittingPlaceLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label personIdLabel;
    @FXML
    private Label routeIdLabel;
    private MyClient main;

    public TicketOverviewController() {
    }

    @FXML
    private void initialize() {

        ticketIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(Long.toString(cellData.getValue().getTicketId())));

        showTicketsOverviewDetails(null);
        ticketTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTicketsOverviewDetails(newValue)
        );

    }

    public void setMain(MyClient main) {
        this.main = main;
        ticketTable.setItems(main.getTicketData());

    }

    @FXML
    private void handlePrev(){ main.showRouteOverview(); }
    @FXML
    private void handleNext(){
        main.showTrainOverview();
    }

    @FXML
    private void handleFind(){
        TableView.TableViewSelectionModel<Ticket> ticketSelectionModel = ticketTable.getSelectionModel();
        boolean okClicked = main.showFindDialog("ticket");
        if (okClicked){
            Ticket ticketToSelect = null;
            for (Ticket ticket: ticketTable.getItems()){
                if (ticket.getTicketId().equals(main.idToSearch)){
                    ticketToSelect = ticket;
                }
            }
            int ticketIndex = ticketTable.getItems().indexOf(ticketToSelect);
            ticketSelectionModel.clearSelection();
            ticketSelectionModel.select(ticketIndex);
        }
    }

    @FXML
    private void handleDeleteTicket() throws IOException {
        int selectedIndex = ticketTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Long ticketId = ticketTable.getItems().get(selectedIndex).getTicketId();
            String targetPath = "/ticket/" + ticketId;
            String response = main.deleteRequest(main.restServerPath + targetPath);
            if (response.equals("")){
                ticketTable.getItems().remove(selectedIndex);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Delete Error");
                alert.setHeaderText("Deletion is impossible here");
                alert.setContentText("You can't delete this ticket because it's still used as a foreign key in another table");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No ticket selection");
            alert.setContentText("Please, select ticket in the table");
            alert.showAndWait();
        }
    }

    private void showTicketsOverviewDetails(Ticket ticket) {
        if (ticket != null) {
            contractDateLabel.setText(DateUtil.format(ticket.getContractDate()));
            sittingPlaceLabel.setText(Integer.toString(ticket.getSittingPlace()));
            priceLabel.setText(Integer.toString(ticket.getPrice()));
            personIdLabel.setText(Long.toString(ticket.getPerson().getPersonId()));
            routeIdLabel.setText(Long.toString(ticket.getRoute().getRouteId()));
        } else {
            contractDateLabel.setText("");
            sittingPlaceLabel.setText("");
            priceLabel.setText("");
            personIdLabel.setText("");
            routeIdLabel.setText("");
        }
    }

    @FXML
    private void handleNewTicket() throws IOException {
        Ticket newTicket = new Ticket();
        boolean okClicked = main.showTicketEditDialog(newTicket,"NEW TICKET");
        if (okClicked) {
            if (main.getTicketData().contains(newTicket)){
                System.out.println("ok");
            }
            String targetPath = "/ticket";
            main.getTicketData().add(newTicket);
            main.postRequest(main.restServerPath + targetPath, newTicket.toJson().toString());
        }
    }

    @FXML
    private void handleEditTicket() throws IOException {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            boolean okClicked = main.showTicketEditDialog(selectedTicket,"EDIT TICKET");
            if (okClicked) {
                showTicketsOverviewDetails(selectedTicket);
                String targetPath = "/ticket/" + selectedTicket.getTicketId();
                main.putRequest(main.restServerPath + targetPath, selectedTicket.toJson().toString());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Ticket Selected");
            alert.setContentText("Please select a ticket in the table.");
            alert.showAndWait();
        }
    }
}
