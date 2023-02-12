package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class OrderService {

   // @Autowired
    OrderRepository orderRepository=new OrderRepository();

    public String addOrder(Order order){
    return  orderRepository.addOrder(order);
    }
    public String addPartner(String id){
        return orderRepository.addPartner(id);
    }
    public String addOrderPartnerPair(String orderId,String partnerId){
        return orderRepository.addOrderPartnerPair(orderId,partnerId);
    }
    public Order getOrderById( String orderId){
        return orderRepository.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById( String partnerId){
        return orderRepository.getPartnerById(partnerId);
    }
    public  Integer getOrderCountByPartnerId( String partnerId){
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }
    public List<String> getAllOrder(){
        return orderRepository.getAllOrder();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }
    public String deletePartnerById(String partnerId) {
        return orderRepository.deletePartnerById(partnerId);
    }
    public String deleteOrderById( String orderId) {
        return orderRepository.deleteOrderById(orderId);
    }
    public String getLastDeliveryTimeByPartnerId( String partnerId) {
       return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }


}
