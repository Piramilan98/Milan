package com.hotel.hotel;

public class BookingSingleton {
    private String rood_id="";
    private int total= 0;
    private static final BookingSingleton booking = new BookingSingleton();

    private BookingSingleton(){}

    public static BookingSingleton getBookingData(){
        return booking;
    }

    public String getRood_id() {
        return rood_id;
    }

    public void setRood_id(String rood_id) {
        this.rood_id = rood_id;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
