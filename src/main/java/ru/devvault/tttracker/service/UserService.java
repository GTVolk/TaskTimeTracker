package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.User;
import ru.devvault.tttracker.util.Result;

public interface UserService {

    Result<User> store(
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            Character adminRole,
            String actionUsername);

    Result<User> remove(String username, String actionUsername);
    Result<User> find(String username, String actionUsername);
    Result<List<User>> findAll(String actionUsername);
    Result<User> findByUsernamePassword(String username, String password);

}