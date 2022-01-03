package com.hotel.hotel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ImportData {

    public ImportData() {
    }

    public static int total_rooms,total_employees,single_rooms,double_rooms,total_guests,booked_rooms,to_clean,avaliable_rooms,receptionists,other_employees;
    public static int[] revenue = new int[12];




    public static int getDataCount(String sql) throws SQLException {
        MySqlConnection connection = new MySqlConnection();
        ResultSet resultSet = connection.connect().createStatement().executeQuery(sql);
        resultSet.next();
        return resultSet.getInt(1);
    }


    public static void refreshAllData(){
        try {
            LocalDate myObj = LocalDate.now();
            total_rooms = getDataCount("select count(*) from RoomData");
            total_guests = getDataCount("select sum(member) from CustomerInfo where leaveDate>='"+myObj+"'");
            total_employees = getDataCount("select count(*) from UserData");
            booked_rooms = getDataCount("select count(*) from RoomData where status='Booked'");
            single_rooms = getDataCount("select count(*) from RoomData where bedtype='Single' and status='Clean'");
            double_rooms = getDataCount("select count(*) from RoomData where bedtype='Double' and status='Clean'");
            to_clean = getDataCount("select count(*) from RoomData where status='Not Clean'");
            receptionists = getDataCount("select count(*) from UserData where type='Receptionist'");
            other_employees = getDataCount("select count(*) from UserData where type<>'Receptionist' and type<>'Admin'");
            avaliable_rooms = single_rooms + double_rooms;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static {
        refreshAllData();
    }

    public static void calculateRevenue(){
            try {
                for (int i =0 ;i < 12;i++)
                    revenue[i] = getDataCount("select sum(totalPrice) from CustomerInfo where MONTH(startDate)='"+(i+1)+"'");
            }catch (SQLException e){
                e.printStackTrace();
            }
    }

}
