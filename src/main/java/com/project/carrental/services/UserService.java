package com.project.carrental.services;

import com.project.carrental.commands.RegisterCommand;
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

    public int registerCommand(String login, String password, int userTypeId) {
        User user = new User();
        user.setUserTypeID(userTypeId);
        user.setLogin(login);
        user.setPassword(password);
        LOGGER.info(user + " registered successfully");
        return userDAO.insert(user);
    }

    public int logInCommandClient(String login) {
        User user = userDAO.findByLogin(login);
        LOGGER.info("User " + user.getLogin() + " logged in");
        return user.getUserID();
    }

    public int logInCommandAdmin(String login) {
        User user = userDAO.findByLogin(login);
        LOGGER.info("Admin " + user.getLogin() + " logged in");
        return user.getUserID();
    }
}