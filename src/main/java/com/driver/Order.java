//package com.driver;
//
//public class Order {
//
//    private String id;
//    private int deliveryTime;
//
//    public Order() {
//    }
//
//    public Order(String id, String deliveryTime) {
//
//        this.id=id;
//
//        // The deliveryTime has to converted from string to int and then stored in the attribute
//        String arr[]=deliveryTime.split(":");
//        //deliveryTime  = HH*60 + MM
//        int hour=Integer.parseInt(arr[0]);
//        int minut=Integer.parseInt(arr[1]);
//        this.deliveryTime=(hour+minut);
//
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public int getDeliveryTime() {return deliveryTime;}
//}
package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        int time = Integer.parseInt(deliveryTime.substring(0, 2)) * 60 + Integer.parseInt(deliveryTime.substring(3));
        this.deliveryTime = time;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}