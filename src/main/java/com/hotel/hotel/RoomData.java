package com.hotel.hotel;

public class RoomData {
    String room_id;
    String bed_type;
    String room_floor;
    String facility;
    String price;
    String status;

    public RoomData(String room_id, String bed_type, String room_floor, String facility, String price, String status) {
        this.room_id = room_id;
        this.bed_type = bed_type;
        this.room_floor = room_floor;
        this.facility = facility;
        this.price = price;
        this.status = status;
    }

    public RoomData(String room_id, String bed_type, String room_floor, String facility, String price) {
        this.room_id = room_id;
        this.bed_type = bed_type;
        this.room_floor = room_floor;
        this.facility = facility;
        this.price = price;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getBed_type() {
        return bed_type;
    }

    public void setBed_type(String bed_type) {
        this.bed_type = bed_type;
    }

    public String getRoom_floor() {
        return room_floor;
    }

    public void setRoom_floor(String room_floor) {
        this.room_floor = room_floor;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
