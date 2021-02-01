package com.project.carrental.daofactory;

import com.project.carrental.dao.*;
import com.project.carrental.idao.*;

/**
 * Factory class for creating DAOs
 *
 */
public class DAOFactory {

    public static IUserTypeDAO getUserTypeDAO() {
        return new UserTypeDAOImpl();
    }

    public static IUserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public static IVehicleDAO getVehicleDAO() {
        return new VehicleDAOImpl();
    }

    public static IPassportDAO getPassportDAO() {
        return new PassportDAOImpl();
    }

    public static IOrderDAO getOrderDAO() {
        return new OrderDAOImpl();
    }
}
