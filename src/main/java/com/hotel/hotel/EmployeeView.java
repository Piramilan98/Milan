package com.hotel.hotel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeView implements Initializable {

    @FXML
    private Label name,employeeid,phone,email,type;

    SettingSingleton employee = SettingSingleton.getSetting();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setText(employee.getName());
        employeeid.setText(employee.getUser_id());
        phone.setText(employee.getPhone());
        email.setText(employee.getEmail());
        type.setText(employee.getType());


    }
}
