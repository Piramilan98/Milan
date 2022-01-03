package com.hotel.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EmployeeEdit implements Initializable {

    @FXML
    TextField name,email,phone;
    @FXML
    private ComboBox<Object> typeCombo;

    MySqlConnection connection = new MySqlConnection();
    SettingSingleton user = SettingSingleton.getSetting();
    ObservableList<Object> typeEmployee = FXCollections.observableArrayList( "Admin","Receptionist","Cleaner","Others");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.getSelectionModel().select(user.getType());
        typeCombo.setItems(typeEmployee);
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());

    }

    public void addnowBtnClicked() throws SQLException {
        String user_name = name.getText();
        String user_email = email.getText();
        String user_phone = phone.getText();
        String user_type = (String) typeCombo.getSelectionModel().getSelectedItem();

        Statement statement = connection.connect().createStatement();
        String sql = "UPDATE UserData SET name='"+user_name+"',type='"+user_type+"',phone='"+user_phone+"',email='"+user_email+"' WHERE id="+user.getUser_id()+";";

        statement.executeUpdate(sql);
        statement.close();

        setEmpty();
    }

    public void setEmpty(){
        name.setText("");
        phone.setText("");
        email.setText("");
        typeCombo.setItems(typeEmployee);
        typeCombo.setStyle("-fx-font-size:20px");
        typeCombo.setStyle("-fx-font-weight: bold");
    }

}

