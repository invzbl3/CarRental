package com.project.carrental.dao;

import com.project.carrental.datasource.ConnectionManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Auxiliary class for DAO classes
 *
 */
public class DAOHelper {

    public static final Logger LOGGER = Logger.getLogger(DAOHelper.class);

    //code representing that executeUpdate command ended with error
    public final static int EXECUTE_UPDATE_ERROR_CODE = -88;

    static Connection getConnection() throws SQLException {
        return ConnectionManager.getInstance().getConnection();
    }

    static void freeConnection(Connection connection) throws SQLException {
        ConnectionManager.getInstance().freeConnection(connection);
    }

    static void closeResources(Connection cn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                freeConnection(cn);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }
}
