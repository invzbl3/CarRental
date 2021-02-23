package com.project.carrental.services;

import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Order;
import com.project.carrental.entities.Passport;
import com.project.carrental.entities.User;
import com.project.carrental.entities.Vehicle;
import com.project.carrental.idao.IOrderDAO;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderService {
    IOrderDAO orderDAO = DAOFactory.getOrderDAO();

    public int confirmOrder(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(true);
        order.setRejected(false);
        order.setRejectDesc(null);
        return orderDAO.update(order);
    }

    public int giveVehicle(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setPicked(true);
        return orderDAO.update(order);
    }

    public int rejectOrder(int orderId, String rejectDesc) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(true);
        order.setRejected(true);
        order.setRejectDesc(rejectDesc);
        return orderDAO.update(order);
    }

    public int resetOrder(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(false);
        order.setRejected(false);
        order.setRejectDesc(null);
        order.setPicked(false);
        order.setReturned(false);
        order.setDamaged(false);
        order.setDamageDesc(null);
        order.setDamageCost(null);
        order.setPaid(false);
        return orderDAO.update(order);
    }

    public int returnVehicle(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setReturned(true);
        return orderDAO.update(order);
    }

    public int returnDamagedVehicle(int orderId, double damageCost, String damageDesc) {
        Order order = orderDAO.findByID(orderId);
        order.setReturned(true);
        order.setDamaged(true);
        order.setDamageDesc(damageDesc);
        order.setDamageCost(BigDecimal.valueOf(damageCost));
        return orderDAO.update(order);
    }

    public int createOrderCommand(Vehicle vehicle, User user, Passport passport,
                                  Timestamp pickUpDate, Timestamp dropOffDate,
                                  BigDecimal rentCost) {
        Order order = new Order();
        order.setVehicle(vehicle);
        order.setUser(user);
        order.setPassport(passport);
        order.setPickUpDate(pickUpDate);
        order.setDropOffDate(dropOffDate);
        order.setRentCost(rentCost);
        return orderDAO.insert(order);
    }
}
