package com.bionic_university.carrental.idao;

import com.bionic_university.carrental.entities.Vehicle;
import java.math.BigDecimal;
import java.util.List;

/**
 * An interface for Vehicle DAO
 *
 */
public interface IVehicleDAO {

    public int insert(Vehicle vehicle);

    public int update(Vehicle vehicle);

    public int delete(Vehicle vehicle);

    public List<Vehicle> findAll();

    public Vehicle findByID(int vehicleIDParam);

    public BigDecimal findDailyPriceByVehicleID(int vehicleID);
}
