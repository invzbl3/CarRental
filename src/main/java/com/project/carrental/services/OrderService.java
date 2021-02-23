package com.project.carrental.services;

import com.project.carrental.dao.DAOHelper;
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

    public void confirmOrder(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(true);
        order.setRejected(false);
        order.setRejectDesc(null);
        int updateOrderCode = orderDAO.update(order);

        if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not updated");
        }
    }

    public void giveVehicle(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setPicked(true);

        int updateOrderCode = orderDAO.update(order);

        if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not updated");
        }
    }

    public void rejectOrder(int orderId, String rejectDesc) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(true);
        order.setRejected(true);
        order.setRejectDesc(rejectDesc);

        int updateOrderCode = orderDAO.update(order);

        if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not updated");
        }
    }

    public void resetOrder(int orderId) {
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

        int updateOrderCode =orderDAO.update(order);

        if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not updated");
        }
    }

    public void returnVehicle(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setReturned(true);

        int updateOrderCode = orderDAO.update(order);

        if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not updated");
        }
    }

    public void returnDamagedVehicle(int orderId, double damageCost, String damageDesc) {
        Order order = orderDAO.findByID(orderId);
        order.setReturned(true);
        order.setDamaged(true);
        order.setDamageDesc(damageDesc);
        order.setDamageCost(BigDecimal.valueOf(damageCost));

        int updateOrderCode =orderDAO.update(order);

        if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not updated");
        }
    }

    public void createOrderCommand(Vehicle vehicle, User user, Passport passport,
                                  Timestamp pickUpDate, Timestamp dropOffDate,
                                  BigDecimal rentCost) {
        Order order = new Order();
        order.setVehicle(vehicle);
        order.setUser(user);
        order.setPassport(passport);
        order.setPickUpDate(pickUpDate);
        order.setDropOffDate(dropOffDate);
        order.setRentCost(rentCost);

        int insertOrderCode = orderDAO.insert(order);

        if (insertOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not created");
        }
    }
}
