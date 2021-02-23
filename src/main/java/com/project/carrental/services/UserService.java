package com.project.carrental.services;

import com.project.carrental.commands.RegisterCommand;
import com.project.carrental.config.ConfigManager;
import com.project.carrental.dao.DAOHelper;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.User;
import com.project.carrental.idao.IUserDAO;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

import static com.project.carrental.commands.ICommand.*;

public class UserService {
    IUserDAO userDAO = DAOFactory.getUserDAO();
    public static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    private static final int LOGIN_ERROR = -1;
    private static final int ACC_TYPE_ADMIN = 1;
    static final int ACC_TYPE_CLIENT = 2;

    public User createOrderCommand(int userID) {
        return userDAO.findByID(userID);
    }

    public void registerCommand(String login, String password, int userTypeId) {
        User user = new User();
        user.setUserTypeID(userTypeId);
        user.setLogin(login);
        user.setPassword(password);

        int insertUserCode = userDAO.insert(user);

        if (insertUserCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Registration failed. Entry was not created");
        }

        LOGGER.info(user + " registered successfully");
    }

    public String logInCommand(String login, String password, HttpSession session) {
        //declare variables
        String page;
        User user;
        int userID;

        /*//check the login information
        switch (checkLogin(login, password)) {
            case ACC_TYPE_CLIENT:
                session.setAttribute(SESS_PARAM_USER_NAME, login);
                session.setAttribute(SESS_PARAM_USERTYPE_ID, ACC_TYPE_CLIENT);
                user = userDAO.findByLogin(login);
                userID = user.getUserID();
                session.setAttribute(SESS_PARAM_USER_ID, userID);
                page = ConfigManager.getInstance()
                        .getProperty(ConfigManager.INDEX_PAGE_PATH);
                LOGGER.info("User " + user.getLogin() + " logged in");
                break;
            case ACC_TYPE_ADMIN:
                session.setAttribute(SESS_PARAM_USER_NAME, login);
                session.setAttribute(SESS_PARAM_USERTYPE_ID, ACC_TYPE_ADMIN);
                user = userDAO.findByLogin(login);
                userID = user.getUserID();
                session.setAttribute(SESS_PARAM_USER_ID, userID);
                page = ConfigManager.getInstance()
                        .getProperty(ConfigManager.INDEX_PAGE_PATH);
                LOGGER.info("Admin " + user.getLogin() + " logged in");
                break;
            case LOGIN_ERROR:
                req.setAttribute(SESS_PARAM_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE);
                page = ConfigManager.getInstance()
                        .getProperty(ConfigManager.ERROR_PAGE_PATH);
                LOGGER.error(login + " login tryout failed");
                break;
            default:
                page = null;
                break;
        }*/

        user = checkLogin(login, password);
        if (user != null) {
            session.setAttribute(SESS_PARAM_USER_NAME, user.getLogin());
            session.setAttribute(SESS_PARAM_USERTYPE_ID, user.getUserTypeID());
            session.setAttribute(SESS_PARAM_USER_ID, user.getUserID());

            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.INDEX_PAGE_PATH);
        } else {
            req.setAttribute(SESS_PARAM_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE);
            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ERROR_PAGE_PATH);
        }

        return page;
    }

    //auxiliary method for checking the login and password correspondence
    private User checkLogin(String login, String password) {
        LOGGER.debug("checkLogin called");
        IUserDAO userDAO = DAOFactory.getUserDAO();
        User user = userDAO.findByLogin(login);
        if ((user == null) || !(user.getPassword().equals(password))) {
            //return LOGIN_ERROR;
            return null;
        } else {
            //return user.getUserTypeID();
            return user;
        }
    }
}