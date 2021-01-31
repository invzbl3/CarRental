package com.bionic_university.carrental.idao;

import com.bionic_university.carrental.entities.Order;
import java.util.List;

/**
 * An interface for Order DAO
 *
 */
public interface IOrderDAO {

    public int insert(Order order);

    public int update(Order order);

    public int delete(Order order);

    public List<Order> findAll();

    public Order findByID(int orderIDParam);
}