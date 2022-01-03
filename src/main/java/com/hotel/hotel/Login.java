package com.hotel.hotel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Login {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    MySqlConnection connection = new MySqlConnection();
    LoggedUser user = LoggedUser.getLoggedUser();


    public void loginBtnClicked(ActionEvent event) throws SQLException, IOException {
        try {

            String user_name = String.valueOf(username.getText());
            String user_password = String.valueOf(password.getText());

            Statement statement = connection.connect().createStatement();
            String sql = "select * from UserData where name='" + user_name + "' and password='" + user_password + "'";

            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                String type = resultSet.getString("type");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                user.setType(type);
                user.setName(name);
                user.setEmail(email);

                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HotelUI.fxml"))));
                stage.setScene(scene);
                stage.show();
            }else {
                Message.errorMessage("Wrong");
            }
        }catch (SQLException e){
            Message.errorMessage(" "+e);
        }

    }

}
