package com.bionic_university.carrental.idao;

import com.bionic_university.carrental.entities.UserType;
import java.util.List;

/**
 * An interface for UserType DAO
 *
 */
public interface IUserTypeDAO {

    public int insert(UserType userType);

    public int update(UserType userType);

    public int delete(UserType userType);

    public List<UserType> findAll();
}
