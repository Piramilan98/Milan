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

public class SettingMenu implements Initializable {
    @FXML
    private TextField name,email,password,phone;
    @FXML
    private ComboBox<Object> typeCombo;

    MySqlConnection connection = new MySqlConnection();
    ObservableList<Object> typeEmployee = FXCollections.observableArrayList( "Admin","Receptionist","Cleaner","Others");

    public void addnowBtnClicked() throws SQLException {
        try {
        String user_name = name.getText();
        String user_mail =email.getText();
        String user_password = password.getText();
        String user_type =(String) typeCombo.getSelectionModel().getSelectedItem();
        String user_phone =phone.getText();

        Statement statement = connection.connect().createStatement();
        String queryData = "('"+user_name+"','"+user_mail+"','"+user_password+"',"+user_phone+",'"+user_type+"');";
        String sql = "INSERT INTO UserData(name,email,password,phone,type) VALUES"+queryData;

        statement.executeUpdate(sql);
        statement.close();
        setEmpty();
        }catch (SQLException e){
            Message.errorMessage(" "+e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.setItems(typeEmployee);
    }

    private void setEmpty(){
        name.setText("");
        typeCombo.setItems(typeEmployee);
        phone.setText("");
        email.setText("");
        password.setText("");
    }
}
