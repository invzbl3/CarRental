package com.project.carrental.services;

import com.project.carrental.commands.RegisterCommand;
import com.project.carrental.dao.DAOHelper;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.User;
import com.project.carrental.idao.IUserDAO;
import org.apache.log4j.Logger;

public class UserService {
    IUserDAO userDAO = DAOFactory.getUserDAO();
    public static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

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

    //auxiliary method for checking the login and password correspondence
    public User checkLogin(String login, String password) {
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