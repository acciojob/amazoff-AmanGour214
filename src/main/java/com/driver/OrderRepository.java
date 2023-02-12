//package com.driver;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//@Repository
//public class OrderRepository {
//
//    HashMap<String, Order> orderDB = new HashMap<>();
//    HashMap<String, DeliveryPartner> partnerDB = new HashMap<>();
//
//    HashMap<String, List<Order>> partnerOrderpair = new HashMap<>();
//    HashSet<String> orderNotAsined = new HashSet<>();
//
//    public String addOrder(Order order) {
//        orderDB.put(order.getId(), order);
//        orderNotAsined.add(order.getId());
//        return "New order added successfully";
//    }
//
//    public String addPartner(String prtnerid) {
//
//        DeliveryPartner partner = new DeliveryPartner(prtnerid);
//        partnerDB.put(prtnerid, partner);
//        return "New delivery partner added successfully";
//
//    }
//
//    public String addOrderPartnerPair(String orderId, String partnrId) {
//
//        DeliveryPartner partner = new DeliveryPartner(partnrId);
//        int pre = partner.getNumberOfOrders() + 1;
//        partner.setNumberOfOrders(pre);
//
//        //partnerDB.put(partnrId,partner);
//
//
//        List<Order> curr;
//
//        if (partnerOrderpair.containsKey(partnrId)) {
//            orderNotAsined.remove(orderId);
//            curr = partnerOrderpair.get(partnrId);
//        } else curr = new ArrayList<Order>();
//
//        curr.add(orderDB.get(orderId));
//
//
//        partnerOrderpair.put(partnrId, curr);
//        return "New order-partner pair added successfully";
//    }
//
//    public Order getOrderById(String orderId) {
//        return orderDB.get(orderId);
//    }
//
//    public DeliveryPartner getPartnerById(String partnerId) {
//        return partnerDB.get(partnerId);
//    }
//
//    public Integer getOrderCountByPartnerId(String partnerId) {
//        int count = partnerOrderpair.get(partnerId).size();
//        return count;
//    }
//
//    public List<String> getOrdersByPartnerId(String partnerId) {
//
//        List<String> orders = new ArrayList<>();
//
//        for (Order order : partnerOrderpair.get(partnerId)) {
//
//            orders.add(order.getId());
//        }
//        return orders;
//
//    }
//
//    public List<String> getAllOrder() {
//        List<String> allOrder = new ArrayList<>();
//
//        for (Order order : orderDB.values()) {
//            allOrder.add(order.getId());
//        }
//        return allOrder;
//    }
//
//    public Integer getCountOfUnassignedOrders() {
//
//       // int count = 0;
//
////        for(String s:orderAsined){
////            boolean isAssined=false;
////            for(Order order:orderDB.values()){
////                if(s.equals(order.getId())){
////                    isAssined=true;
////                }
////            }
////            if(isAssined==false)count++;
////        }
//        return orderNotAsined.size();
//
//
//    }
//
//    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
//
//        int currTime=(Integer.parseInt(time.substring(0,2))*60)+(Integer.parseInt(time.substring(3)));
//        int orderLeft=0;
//        for(Order order:partnerOrderpair.get(partnerId)){
//            int previustime=order.getDeliveryTime();
//            if(previustime>currTime)orderLeft++;
//        }
//        return orderLeft;
//    }
//    public String getLastDeliveryTimeByPartnerId( String partnerId) {
//
//        int lastTime=0;
//
//        for(Order order: partnerOrderpair.get(partnerId)){
//            if(order.getDeliveryTime()>lastTime){
//                lastTime=order.getDeliveryTime();
//            }
//        }
//        int hour=lastTime/60;
//        int minut=lastTime%60;
//
//        String HH=Integer.toString(hour);
//        String MM=Integer.toString(minut);
//
//        if(HH.length()==1){
//            HH="0"+HH;
//        }
//        if(MM.length()==1){
//            MM="0"+MM;
//        }
//        return HH+":"+MM;
//    }
//
//    public String deletePartnerById(String partnerId) {
//
//        if(partnerOrderpair.containsKey(partnerId)){
//            List<Order>newUnassined=partnerOrderpair.get(partnerId);
//
//            for (Order order : newUnassined){
//                orderNotAsined.add(order.getId());
//            }
//        }
//
//        partnerOrderpair.remove(partnerId);
//        partnerDB.remove(partnerId);
//        return " removed successfully";
//    }
//
//    public String deleteOrderById( String orderId) {
//
//        if(orderDB.containsKey(orderId)){
//            orderDB.remove(orderId);
//            orderNotAsined.remove(orderId);
//        }
//        for(List<Order>list:partnerOrderpair.values()){
//
//            Order order=orderDB.get(orderId);
//
//            if(list.contains(order))list.remove(order);
//        }
//        return " removed successfully";
//    }
//
//
//
//
//}
package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderDb = new HashMap<>();
    HashMap<String, DeliveryPartner> partnerDb = new HashMap<>();
    HashMap<String, List<String>> pairDb = new HashMap<>();
    HashMap<String, String> assignedDb = new HashMap<>(); // <orderId, partnerId>

    public String addOrder(Order order) {
        orderDb.put(order.getId(), order);
        return "Added";
    }

    public String addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerDb.put(partnerId, partner);
        return "Added";
    }

    public String addOrderPartnerPair(String orderId, String partnerId) {
        // This is basically assigning that order to that partnerId
        List<String> list = pairDb.getOrDefault(partnerId, new ArrayList<>());
        list.add(orderId);
        pairDb.put(partnerId, list);
        assignedDb.put(orderId, partnerId);
        DeliveryPartner partner = partnerDb.get(partnerId);
        partner.setNumberOfOrders(list.size());
        return "Added";

    }

    public Order getOrderById(String orderId) {
        // order should be returned with an orderId.
//        for (String s : orderDb.keySet()) {
//            if (s.equals(orderId)) {
//                return orderDb.get(s);
//            }
//        }
//        return null;
        return orderDb.getOrDefault(orderId,null);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        // deliveryPartner should contain the value given by partnerId

//        if (partnerDb.containsKey(partnerId)) {
//            return partnerDb.get(partnerId);
//        }
        return partnerDb.getOrDefault(partnerId,null);
//        return null;

    }

    public int getOrderCountByPartnerId(String partnerId) {
        // orderCount should denote the orders given by a partner-id
        int orders = pairDb.getOrDefault(partnerId, new ArrayList<>()).size();
        return orders;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        // orders should contain a list of orders by PartnerId

        List<String> orders = pairDb.getOrDefault(partnerId, new ArrayList<>());
        return orders;
    }

    public List<String> getAllOrders() {
        // Get all orders
        List<String> orders = new ArrayList<>();
        for (String s : orderDb.keySet()) {
            orders.add(s);
        }
        return orders;

    }

    public int getCountOfUnassignedOrders() {
        // Count of orders that have not been assigned to any DeliveryPartner
        int countOfOrders = orderDb.size() - assignedDb.size();
        return countOfOrders;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        // countOfOrders that are left after a particular time of a DeliveryPartner
        int countOfOrders = 0;
        List<String> list = pairDb.get(partnerId);
        int deliveryTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3));
        for (String s : list) {
            Order order = orderDb.get(s);
            if (order.getDeliveryTime() > deliveryTime) {
                countOfOrders++;
            }
        }
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        // Return the time when that partnerId will deliver his last delivery order.
        String time = "";
        List<String> list = pairDb.get(partnerId);
        int deliveryTime = 0;
        for (String s : list) {
            Order order = orderDb.get(s);
            deliveryTime = Math.max(deliveryTime, order.getDeliveryTime());
        }
        int hour = deliveryTime / 60;
        String sHour = "";
        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }

        int min = deliveryTime % 60;
        String sMin = "";
        if (min < 10) {
            sMin = "0" + String.valueOf(min);
        } else {
            sMin = String.valueOf(min);
        }

        time = sHour + ":" + sMin;

        return time;

    }

    public String deletePartnerById(String partnerId) {
        // Delete the partnerId
        // And push all his assigned orders to unassigned orders.
        partnerDb.remove(partnerId);

        List<String> list = pairDb.getOrDefault(partnerId, new ArrayList<>());
        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            assignedDb.remove(s);
        }
        pairDb.remove(partnerId);
        return "Deleted";
    }

    public String deleteOrderById(String orderId) {

        // Delete an order and also
        // remove it from the assigned order of that partnerId
        orderDb.remove(orderId);
        String partnerId = assignedDb.get(orderId);
        assignedDb.remove(orderId);
        List<String> list = pairDb.get(partnerId);

        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            if (s.equals(orderId)) {
                itr.remove();
            }
        }
        pairDb.put(partnerId, list);

        return "Deleted";

    }
}