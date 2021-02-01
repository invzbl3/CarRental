package com.project.carrental.dao;

import com.project.carrental.entities.Passport;
import com.project.carrental.idao.IPassportDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for "passports" table
 *
 */
public class PassportDAOImpl implements IPassportDAO {

    public static final Logger LOGGER = Logger.getLogger(PassportDAOImpl.class);
    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private final String TABLE_NAME = "passports";
    private final String COL_1 = "passport_id";
    private final String COL_2 = "last_name";
    private final String COL_3 = "first_name";
    private final String COL_4 = "patronymic";
    private final String COL_5 = "birthday";
    private final String COL_6 = "p_series";
    private final String COL_7 = "p_number";
    private final String COL_8 = "who_issued";
    private final String COL_9 = "when_issued";

    private final String INSERT_QUERY;
    private final String UPDATE_QUERY;
    private final String DELETE_QUERY;
    private final String SELECT_QUERY;

    {
        INSERT_QUERY = "INSERT INTO " +
                TABLE_NAME +
                " (" +
                COL_2 + "," +
                COL_3 + "," +
                COL_4 + "," +
                COL_5 + "," +
                COL_6 + "," +
                COL_7 + "," +
                COL_8 + "," +
                COL_9 +
                ") VALUES " +
                "(?,?,?,?,?,?,?,?)";

        UPDATE_QUERY = "UPDATE " +
                TABLE_NAME +
                " SET " +
                COL_2 + "=?" + "," +
                COL_3 + "=?" + "," +
                COL_4 + "=?" + "," +
                COL_5 + "=?" + "," +
                COL_6 + "=?" + "," +
                COL_7 + "=?" + "," +
                COL_8 + "=?" + "," +
                COL_9 + "=?" +
                " WHERE " +
                COL_1 + "=?";

        DELETE_QUERY = "DELETE FROM " +
                TABLE_NAME +
                " WHERE " +
                COL_1 + "=?";

        SELECT_QUERY = "SELECT * FROM " +
                TABLE_NAME;
    }

    @Override
    public int insert(Passport passport) {
        int autoIncID = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, passport.getLastName());
            ps.setString(2, passport.getFirstName());
            ps.setString(3, passport.getPatronymic());
            ps.setDate(4, passport.getBirthday());
            ps.setString(5, passport.getPassportSeries());
            ps.setString(6, passport.getPassportNumber());
            ps.setString(7, passport.getWhoIssued());
            ps.setDate(8, passport.getWhenIssued());
            ps.executeUpdate();
            ResultSet keysSet = ps.getGeneratedKeys();
            keysSet.next();
            autoIncID = keysSet.getInt(1);
            LOGGER.info("Data inserted successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return autoIncID;
    }

    @Override
    public int update(Passport passport) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(UPDATE_QUERY);
            ps.setString(1, passport.getLastName());
            ps.setString(2, passport.getFirstName());
            ps.setString(3, passport.getPatronymic());
            ps.setDate(4, passport.getBirthday());
            ps.setString(5, passport.getPassportSeries());
            ps.setString(6, passport.getPassportNumber());
            ps.setString(7, passport.getWhoIssued());
            ps.setDate(8, passport.getWhenIssued());
            ps.setInt(9, passport.getPassportID());
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
    public int delete(Passport passport) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(DELETE_QUERY);
            ps.setInt(1, passport.getPassportID());
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
    public List<Passport> findAll() {
        List<Passport> list = new ArrayList<>();
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                int passportID = rs.getInt(1);
                String lastName = rs.getString(2);
                String firstName = rs.getString(3);
                String patronymic = rs.getString(4);
                Date birthday = rs.getDate(5);
                String pSeries = rs.getString(6);
                String pNumber = rs.getString(7);
                String whoIssued = rs.getString(8);
                Date whenIssued = rs.getDate(9);
                Passport passportObj = new Passport(passportID, lastName,
                        firstName, patronymic, birthday, pSeries, pNumber,
                        whoIssued, whenIssued);
                list.add(passportObj);
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
    public Passport findByID(int passportIDParam) {
        Passport passportObj = null;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_QUERY + " WHERE passport_id=?");
            ps.setInt(1, passportIDParam);
            rs = ps.executeQuery();
            rs.next();
            int passportID = rs.getInt(1);
            String lastName = rs.getString(2);
            String firstName = rs.getString(3);
            String patronymic = rs.getString(4);
            Date birthday = rs.getDate(5);
            String pSeries = rs.getString(6);
            String pNumber = rs.getString(7);
            String whoIssued = rs.getString(8);
            Date whenIssued = rs.getDate(9);
            passportObj = new Passport(passportID, lastName, firstName,
                    patronymic, birthday, pSeries, pNumber,
                    whoIssued, whenIssued);
            LOGGER.info("Data selected successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return passportObj;
    }
}