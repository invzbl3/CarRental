package com.project.carrental.services;

import com.project.carrental.dao.DAOHelper;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Passport;
import com.project.carrental.idao.IPassportDAO;

public class PassportService {
    IPassportDAO passportDAO = DAOFactory.getPassportDAO();

    public void createPassport(Passport passport) {

        int passportID = passportDAO.insert(passport);

        if (passportID == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Passport entry in DB was not created");
        } else {
            passport.setPassportID(passportID);
        }
    }
}
