package com.bionic_university.carrental.datasource;

import com.bionic_university.carrental.dao.UserTypeDAOImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Class that provides the ability to use Apache Tomcat
 * Connection Pool
 *
 */
public class ConnectionPool {

    public static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    InitialContext initialContext;
    DataSource dataSource;

    {
        try {
            initialContext = new InitialContext();
            dataSource = (javax.sql.DataSource) initialContext
                    .lookup("java:comp/env/jdbc/appname");
        } catch (NamingException e) {
            LOGGER.error(e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        LOGGER.debug("getConnection called" + connection);
        return connection;
    }

    public void freeConnection(Connection connection) throws SQLException {
        LOGGER.debug("freeConnection called" + connection);
        connection.close();
    }
}
