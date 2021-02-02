package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.User;
import java.util.List;

public interface UserDao extends GenericDao<User, String> {

    List<User> findAll();
    User findByUsernamePassword(String username, String password);
    User findByUsername(String username);
    User findByEmail(String email);
}
