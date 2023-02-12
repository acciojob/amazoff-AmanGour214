package com.driver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderDB = new HashMap<>();
    HashMap<String, DeliveryPartner> partnerDB = new HashMap<>();

    HashMap<String, List<Order>> partnerOrderpair = new HashMap<>();
    HashSet<String> orderNotAsined = new HashSet<>();

    public String addOrder(Order order) {
        orderDB.put(order.getId(), order);
        orderNotAsined.add(order.getId());
        return "New order added successfully";
    }

    public String addPartner(String prtnerid) {

        DeliveryPartner partner = new DeliveryPartner(prtnerid);
        partnerDB.put(prtnerid, partner);
        return "New delivery partner added successfully";

    }

    public String addOrderPartnerPair(String orderId, String partnrId) {

        DeliveryPartner partner = new DeliveryPartner(partnrId);
        int pre = partner.getNumberOfOrders() + 1;
        partner.setNumberOfOrders(pre);

        //partnerDB.put(partnrId,partner);


        List<Order> curr;

        if (partnerOrderpair.containsKey(partnrId)) {
            orderNotAsined.remove(orderId);
            curr = partnerOrderpair.get(partnrId);
        } else curr = new ArrayList<Order>();

        curr.add(orderDB.get(orderId));


        partnerOrderpair.put(partnrId, curr);
        return "New order-partner pair added successfully";
    }

    public Order getOrderById(String orderId) {
        return orderDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        int count = partnerOrderpair.get(partnerId).size();
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {

        List<String> orders = new ArrayList<>();

        for (Order order : partnerOrderpair.get(partnerId)) {

            orders.add(order.getId());
        }
        return orders;

    }

    public List<String> getAllOrder() {
        List<String> allOrder = new ArrayList<>();

        for (Order order : orderDB.values()) {
            allOrder.add(order.getId());
        }
        return allOrder;
    }

    public Integer getCountOfUnassignedOrders() {

       // int count = 0;

//        for(String s:orderAsined){
//            boolean isAssined=false;
//            for(Order order:orderDB.values()){
//                if(s.equals(order.getId())){
//                    isAssined=true;
//                }
//            }
//            if(isAssined==false)count++;
//        }
        return orderNotAsined.size();


    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {

        int currTime=(Integer.parseInt(time.substring(0,2))*60)+(Integer.parseInt(time.substring(3)));
        int orderLeft=0;
        for(Order order:partnerOrderpair.get(partnerId)){
            int previustime=order.getDeliveryTime();
            if(previustime>currTime)orderLeft++;
        }
        return orderLeft;
    }
    public String getLastDeliveryTimeByPartnerId( String partnerId) {

        int lastTime=0;

        for(Order order: partnerOrderpair.get(partnerId)){
            if(order.getDeliveryTime()>lastTime){
                lastTime=order.getDeliveryTime();
            }
        }
        int hour=lastTime/60;
        int minut=lastTime%60;

        String HH=Integer.toString(hour);
        String MM=Integer.toString(minut);

        if(HH.length()==1){
            HH="0"+HH;
        }
        if(MM.length()==1){
            MM="0"+MM;
        }
        return HH+":"+MM;
    }

    public String deletePartnerById(String partnerId) {

        if(partnerOrderpair.containsKey(partnerId)){
            List<Order>newUnassined=partnerOrderpair.get(partnerId);

            for (Order order : newUnassined){
                orderNotAsined.add(order.getId());
            }
        }

        partnerOrderpair.remove(partnerId);
        partnerDB.remove(partnerId);
        return " removed successfully";
    }

    public String deleteOrderById( String orderId) {

        if(orderDB.containsKey(orderId)){
            orderDB.remove(orderId);
            orderNotAsined.remove(orderId);
        }
        for(List<Order>list:partnerOrderpair.values()){

            Order order=orderDB.get(orderId);

            if(list.contains(order))list.remove(order);
        }
        return " removed successfully";
    }




}
