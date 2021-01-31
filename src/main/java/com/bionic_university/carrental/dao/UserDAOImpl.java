package com.bionic_university.carrental.dao;

import com.bionic_university.carrental.config.ConfigManager;
import com.bionic_university.carrental.entities.User;
import com.bionic_university.carrental.idao.IUserDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for "users" table
 *
 */
public class UserDAOImpl implements IUserDAO {

    public static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);
    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private final String TABLE_NAME = "users";
    private final String COL_1 = "user_id";
    private final String COL_2 = "usertype_id";
    private final String COL_3 = "login";
    private final String COL_4 = "password";

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
                COL_4 +
                ") VALUES " +
                "(?,?,?)";

        UPDATE_QUERY = "UPDATE " +
                TABLE_NAME +
                " SET " +
                COL_2 + "=?" + "," +
                COL_3 + "=?" + "," +
                COL_4 + "=?" +
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
    public int insert(User user) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(INSERT_QUERY);
            ps.setInt(1, user.getUserTypeID());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
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
    public int update(User user) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(UPDATE_QUERY);
            ps.setInt(1, user.getUserTypeID());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getUserID());
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
    public int delete(User user) {
        int result = DAOHelper.EXECUTE_UPDATE_ERROR_CODE;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(DELETE_QUERY);
            ps.setInt(1, user.getUserID());
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
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(1);
                int userTypeID = rs.getInt(2);
                String login = rs.getString(3);
                String password = rs.getString(4);
                User userObj = new User(userID, userTypeID, login, password);
                list.add(userObj);
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
    public User findByLogin(String loginParam) {
        User userObj = null;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_QUERY + " WHERE login=?");
            ps.setString(1, loginParam);
            rs = ps.executeQuery();
            rs.next();
            int userID = rs.getInt(1);
            int userTypeID = rs.getInt(2);
            String login = rs.getString(3);
            String password = rs.getString(4);
            userObj = new User(userID, userTypeID, login, password);
            LOGGER.info("Data selected successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return userObj;
    }

    @Override
    public User findByID(int userIDParam) {
        User userObj = null;
        try {
            cn = DAOHelper.getConnection();
            ps = cn.prepareStatement(SELECT_QUERY + " WHERE user_id=?");
            ps.setInt(1, userIDParam);
            rs = ps.executeQuery();
            rs.next();
            int userID = rs.getInt(1);
            int userTypeID = rs.getInt(2);
            String login = rs.getString(3);
            String password = rs.getString(4);
            userObj = new User(userID, userTypeID, login, password);
            LOGGER.info("Data selected successfully");
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            DAOHelper.closeResources(cn, ps, rs);
        }
        return userObj;
    }

}
