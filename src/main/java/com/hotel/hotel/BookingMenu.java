package com.hotel.hotel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;


public class BookingMenu implements Initializable {

    @FXML
    private DatePicker startDate,leaveDate;
    @FXML
    private TextField name,nic,phone,city,members;

    @FXML
     private Label roomID,total;

    MySqlConnection connection = new MySqlConnection();
    BookingSingleton booking = BookingSingleton.getBookingData();

    public void booknowBtnClicked() throws SQLException {
        try {
            String customer_name = name.getText();
            String customer_nic = nic.getText();
            String customer_phone = phone.getText();
            String customer_city = city.getText();
            String customer_roomID = booking.getRood_id();
            String customer_members = members.getText();
            String customer_startDate = startDate.getValue().toString();
            String customer_leaveDate = leaveDate.getValue().toString();

            if (customer_leaveDate != null && customer_startDate != null) {
                long booking_days = DAYS.between(LocalDate.parse(customer_startDate), LocalDate.parse(customer_leaveDate));
                String customer_total = String.valueOf(booking_days * booking.getTotal());

                Statement statement = connection.connect().createStatement();
                String queryData = "(" + customer_roomID + ",'" + customer_name + "','" + customer_nic + "'," + customer_phone + ",'" + customer_city + "','" + customer_members + "'," + customer_total + ",'" + customer_startDate + "','" + customer_leaveDate + "');";
                String sql = "INSERT INTO CustomerInfo(RoomID,name,nic,phone,city,member,totalPrice,startDate,leaveDate) VALUES" + queryData;
                String sqlChangeRoom = "UPDATE RoomData SET Status = 'Booked' WHERE RoomID = '" + booking.getRood_id() + "';";

                statement.executeUpdate(sql);
                statement.executeUpdate(sqlChangeRoom);
                statement.close();

                setEmpty();

                Message.infoMessage("Your Booking Total Amount Is " + customer_total);
            }else{
                Message.errorMessage("Some Error");
            }
        }catch (Exception e){
            Message.errorMessage(" "+e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomID.setText(booking.getRood_id());
        total.setText(String.valueOf(booking.getTotal()));
    }

    private void setEmpty(){
        name.setText("");
        nic.setText("");
        phone.setText("");
        city.setText("");
        members.setText("");
        total.setText("");
        roomID.setText("");
    }
}
