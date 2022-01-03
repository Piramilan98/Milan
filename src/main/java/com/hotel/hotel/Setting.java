package com.hotel.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class Setting implements Initializable {

    @FXML
    private TableView<UserData> userTable;
    @FXML
    private TableColumn<UserData,String> idCol;
    @FXML
    private TableColumn<UserData,String> nameCol;
    @FXML
    private TableColumn<UserData,String> typeCol;
    @FXML
    private TableColumn<UserData,String> phoneCol;
    @FXML
    private TableColumn<UserData,String> mailCol;

    @FXML
    private Label employees,receptionist,other;

    private int index=-1;

    ObservableList<UserData> UserList = FXCollections.observableArrayList();
    MySqlConnection connection = new MySqlConnection();
    SettingSingleton user = SettingSingleton.getSetting();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();

    }


    public void getSelected() {
        index = userTable.getSelectionModel().getSelectedIndex();
        if (index >= 0){
            user.setUser_id(idCol.getCellData(index));
            user.setName(nameCol.getCellData(index));
            user.setPhone(phoneCol.getCellData(index));
            user.setEmail(mailCol.getCellData(index));
            user.setType(typeCol.getCellData(index));
        }

    }


    public void btnClicked(ActionEvent event) throws IOException, SQLException {
        Button button = (Button) event.getSource();
        String btnType = button.getText();
        if (btnType.equals("Add"))
            loadFXML("SettingMenu");
        else if(btnType.equals("View") && index >= 0)
            loadFXML("EmployeeView");
        else if(btnType.equals("Edit")  && index >= 0)
            loadFXML("EmployeeEdit");
        else if (btnType.equals("Remove") && index >= 0)
            removeEmployee();
    }

    private void loadFXML(String file_name) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file_name + "UI.fxml")));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Setting Menu");

        stage.setScene(new Scene(parent));
        stage.show();
    }

    private void removeEmployee() throws SQLException {
        if (index >= 0) {
            String id = user.getUser_id();
            Statement statement = connection.connect().createStatement();
            String sqlRemoveRoom = "DELETE UserData FROM UserData WHERE ID = '"+id+ "';";
            statement.executeUpdate(sqlRemoveRoom);
            statement.close();
        }
    }

    private void updateTable(){
        ImportData.refreshAllData();
        MySqlConnection connection = new MySqlConnection();
        String SQL = "SELECT * FROM UserData";
        try {
            ResultSet resultSet = connection.connect().createStatement().executeQuery(SQL);
            while (resultSet.next()){
                UserList.add(new UserData(resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("type"),
                        resultSet.getString("phone"),
                        resultSet.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        mailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        userTable.setItems(UserList);



        employees.setText(String.valueOf(ImportData.total_employees));
        receptionist.setText(String.valueOf(ImportData.receptionists));
        other.setText(String.valueOf(ImportData.other_employees));
    }

    public void refreshSettingBtn() {
        userTable.getItems().clear();
        updateTable();
    }
}
