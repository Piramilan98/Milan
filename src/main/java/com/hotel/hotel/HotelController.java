package com.hotel.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HotelController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane home;

    @FXML
    private PieChart pieChart;
    @FXML
    private Label totalRooms,bookedRooms,totalGuests, totalEmployee, avaliableRooms, singleRooms, doubleRooms, toCleanRooms;
    @FXML
    private NumberAxis y;

    @FXML
    private CategoryAxis x;

    @FXML
    private javafx.scene.chart.LineChart<?, ?> LineChart;

    @FXML
    private Button settingBtn;

    @FXML
    private Label userName,userMail;

    LoggedUser user = LoggedUser.getLoggedUser();

    public HotelController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        if (!user.getType().equals("Admin")){
            settingBtn.setDisable(true);
            settingBtn.setStyle("-fx-background-color:#252525; -fx-fill:#252525;");
        }

        userName.setText(user.getName());
        userMail.setText(user.getEmail());
        updateAllData();
    }

    private void loadFXML(String file_name) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file_name + "UI.fxml")));
        }catch (IOException e){
            System.out.println("The Error "+e);
        }

        borderPane.setCenter(root);
    }

    public void btnClicked(Event event) throws IOException {
        Button button = (Button) event.getSource();
        String ui_type = button.getText();

        if (!ui_type.equals("Home"))
            loadFXML(ui_type);
        else {
            borderPane.setCenter(home);
            updateAllData();
        }
    }

    public void logoutBtnClicked() {
        Message.logoutMessage();
    }

    private void updateAllData(){

        pieChart.getData().clear();
        LineChart.getData().clear();
        ImportData.refreshAllData();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Booked",ImportData.booked_rooms),
                new PieChart.Data("To Clean",ImportData.to_clean),
                new PieChart.Data("Avaliable",ImportData.avaliable_rooms));
        pieChart.setData(pieChartData);

        XYChart.Series series = new XYChart.Series();

        ImportData.calculateRevenue();
        String[] monthArr = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (int i= 0;i<12;i++){
            series.getData().add(new XYChart.Data(monthArr[i],ImportData.revenue[i]));
        }

        LineChart.getData().addAll(series);

        totalRooms.setText(String.valueOf(ImportData.total_rooms));
        totalEmployee.setText(String.valueOf(ImportData.total_employees));
        totalGuests.setText(String.valueOf(ImportData.total_guests));
        bookedRooms.setText(String.valueOf(ImportData.booked_rooms));
        avaliableRooms.setText(String.valueOf(ImportData.avaliable_rooms));
        singleRooms.setText(String.valueOf(ImportData.single_rooms));
        doubleRooms.setText(String.valueOf(ImportData.double_rooms));
        toCleanRooms.setText(String.valueOf(ImportData.to_clean));
    }
}
