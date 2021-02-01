package com.project.carrental.idao;

import com.project.carrental.entities.Passport;

import java.util.List;

/**
 * An interface for Passport DAO
 *
 */
public interface IPassportDAO {

    public int insert(Passport passport);

    public int update(Passport passport);

    public int delete(Passport passport);

    public List<Passport> findAll();

    public Passport findByID(int passportIDParam);
}