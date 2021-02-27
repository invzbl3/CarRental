package com.project.carrental.services;

import com.project.carrental.commands.CalculateCostCommand;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Vehicle;
import com.project.carrental.idao.IVehicleDAO;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class VehicleService {
    public static final Logger LOGGER = Logger.getLogger(CalculateCostCommand.class);
    IVehicleDAO vehicleDAO = DAOFactory.getVehicleDAO();

    public BigDecimal calculateCost(int vehicleID, Timestamp pick, Timestamp drop) {
        int rentInterval = daysBetween(pick, drop);
        BigDecimal dailyPrice = vehicleDAO.findDailyPriceByVehicleID(vehicleID);
        return calcRentCost(dailyPrice, rentInterval);
    }

    //auxiliary method for calculating rent cost based on daily price and number
    //of days
    private BigDecimal calcRentCost(BigDecimal dailyPrice, int days) {
        return dailyPrice.multiply(new BigDecimal(days));
    }

    //auxiliary method for converting date format from HTML to Java
    static String convertDateFormat(String htmlDate) {
        String[] separateDateTime = htmlDate.split("T");
        return separateDateTime[0] +
                " " +
                separateDateTime[1] +
                ":00";
    }

    //auxiliary method for counting number of days between two Timestamp objects
    private int daysBetween(java.sql.Timestamp ts1, java.sql.Timestamp ts2) {
        DateTime firstDateTime;
        DateTime secondDateTime;
        if (ts2.after(ts1)) {
            firstDateTime = new DateTime(ts1.getTime());
            secondDateTime = new DateTime(ts2.getTime());
        } else {
            LOGGER.warn("Second parameter date is before first");
            firstDateTime = new DateTime(ts2.getTime());
            secondDateTime = new DateTime(ts1.getTime());
        }
        return Days.daysBetween(firstDateTime.withTimeAtStartOfDay(),
                secondDateTime.withTimeAtStartOfDay()).getDays();
    }

    public Vehicle createOrderCommand(int vehicleID) {
        return vehicleDAO.findByID(vehicleID);
    }
}
