package com.project.carrental.services;

import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Passport;
import com.project.carrental.idao.IPassportDAO;

public class PassportService {
    IPassportDAO passportDAO = DAOFactory.getPassportDAO();


    public int createOrder(Passport passport) {
        return passportDAO.insert(passport);
    }
}
