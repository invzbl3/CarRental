package com.project.carrental.dao;

import com.project.carrental.entities.Vehicle;
import com.project.carrental.idao.IVehicleDAO;
import org.apache.log4j.Logger;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for "vehicles" table
 *
 */
public class VehicleDAOImpl implements IVehicleDAO {

    public static final Logger LOGGER = Logger.getLogger(VehicleDAOImpl.class);
    private Connection cn;
    private PreparedStatement ps;
    private ResultSet rs;

    private final String TABLE_NAME = "vehicles";
    private final String COL_1 = "vehicle_id";
    private final String COL_2 = "make";
    private final String COL_3 = "model";
    private final String COL_4 = "auto_gearbox";
    private final String COL_5 = "air_conditioner";
    private final String COL_6 = "seats";
    private final String COL_7 = "daily_price";

    private final String INSERT_QUERY;
    private final String UPDATE_QUERY;
    private final String DELETE_QUERY;
    private final String SELECT_QUERY;
    private final String SELECT_DAILY_PRICE_QUERY;

    {

        INSERT_QUERY = "INSERT INTO " +
                TABLE_NAME +
                " (" +
                COL_2 + "," +
                COL_3 + "," +
                COL_4 + "," +
                COL_5 + "," +
                COL_6 + "," +
                COL_7 +
                ") VALUES " +
                "(?,?,?,?,?,?)";

        UPDATE_QUERY = "UPDATE " +
                TABLE_NAME +
                " SET " +
                COL_2 + "=?" + "," +
                COL_3 + "=?" + "," +
                COL_4 + "=?" + "," +
                COL_5 + "=?" + "," +
                COL_6 + "=?" + "," +
                COL_7 + "=?" +
                " WHERE " +
                COL_1 + "=?";

        DELETE_QUERY = "DELETE FROM " +
                TABLE_NAME +
                " WHERE " +
                COL_1 + "=?";

        SELECT_QUERY = "SELECT " +
                "*" +
                " FROM " +
                TABLE_NAME;
        SELECT_DAILY_PRICE_QUERY = "SELECT " +
                COL_7 +
                " FROM " +
                TABLE_NAME +
                " WHERE " +
                COL_1 + "=?";
    }

    @Override
    public int insert(Vehicle vehicle) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(INSERT_QUERY);
            ps.setString(1, vehicle.getMake());
            ps.setString(2, vehicle.getModel());
            ps.setBoolean(3, vehicle.isAutoGearbox());
            ps.setBoolean(4, vehicle.isAirConditioner());
            ps.setInt(5, vehicle.getSeats());
            ps.setBigDecimal(6, vehicle.getDailyPrice());
            result = ps.executeUpdate();
            LOGGER.info("Data inserted successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return result;
    }

    @Override
    public int update(Vehicle vehicle) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(UPDATE_QUERY);
            ps.setString(1, vehicle.getMake());
            ps.setString(2, vehicle.getModel());
            ps.setBoolean(3, vehicle.isAutoGearbox());
            ps.setBoolean(4, vehicle.isAirConditioner());
            ps.setInt(5, vehicle.getSeats());
            ps.setBigDecimal(6, vehicle.getDailyPrice());
            ps.setInt(7, vehicle.getVehicleID());
            result = ps.executeUpdate();
            LOGGER.info("Data updated successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return result;
    }

    @Override
    public int delete(Vehicle vehicle) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(DELETE_QUERY);
            ps.setInt(1, vehicle.getVehicleID());
            result = ps.executeUpdate();
            LOGGER.info("Data deleted successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return result;
    }

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> list = new ArrayList<>();
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                int vehicleID = rs.getInt(1);
                String make = rs.getString(2);
                String model = rs.getString(3);
                boolean autoGearbox = rs.getBoolean(4);
                boolean airConditioner = rs.getBoolean(5);
                int seats = rs.getInt(6);
                BigDecimal dailyPrice = rs.getBigDecimal(7);
                Vehicle vehicleObj = new Vehicle(vehicleID, make, model,
                        autoGearbox, airConditioner, seats, dailyPrice);
                list.add(vehicleObj);
            }
            LOGGER.info("Data selected successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return list;
    }

    @Override
    public Vehicle findByID(int vehicleIDParam) {
        Vehicle vehicleObj = null;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_QUERY + " WHERE vehicle_id=?");
            ps.setInt(1, vehicleIDParam);
            rs = ps.executeQuery();
            rs.next();
            int vehicleID = rs.getInt(1);
            String make = rs.getString(2);
            String model = rs.getString(3);
            boolean autoGearbox = rs.getBoolean(4);
            boolean airConditioner = rs.getBoolean(5);
            int seats = rs.getInt(6);
            BigDecimal dailyPrice = rs.getBigDecimal(7);
            vehicleObj = new Vehicle(vehicleID, make, model, autoGearbox,
                    airConditioner, seats, dailyPrice);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return vehicleObj;
    }

    @Override
    public BigDecimal findDailyPriceByVehicleID(int vehicleID) {
        BigDecimal dailyPrice = null;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_DAILY_PRICE_QUERY);
            ps.setInt(1, vehicleID);
            rs = ps.executeQuery();
            rs.next();
            dailyPrice = rs.getBigDecimal(1);
            LOGGER.info("Data selected successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return dailyPrice;
    }
}