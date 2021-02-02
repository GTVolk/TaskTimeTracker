package ru.devvault.tttracker.service;

import ru.devvault.tttracker.dao.TaskLogDao;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.devvault.tttracker.entity.User;
import ru.devvault.tttracker.util.Result;
import ru.devvault.tttracker.util.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    protected TaskLogDao taskLogDao;
    
    public UserServiceImpl() {
        super();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<User> find(String username, String actionUsername) {
        if (isValidUser(actionUsername)) {
            return ResultFactory.getSuccessResult(userDao.findByUsername(username));
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result<User> store(
        String username,
        String firstName,
        String lastName,
        String email,
        String password,
        Character adminRole,
        String actionUsername
    ) {

        User actionUser = userDao.find(actionUsername);
        
        if (!actionUser.isAdmin()) {
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty() ) {
            return ResultFactory.getFailResult("Unable to store a user without a valid non empty username/email");
        }

        if (password == null || password.length() == 0) {
            return ResultFactory.getFailResult("Unable to store a user without a valid non empty password");
        }

        if (!adminRole.equals('Y') && !adminRole.equals('N')) {
            return ResultFactory.getFailResult("Unable to store the user: adminRole must be Y or N");
        }

        username = username.trim();
        email = email.trim();

        User user = userDao.findByUsername(username);

        User testByEmailUser = userDao.findByEmail(email);
        boolean doInsert = false;

        if (user == null) {
            if (testByEmailUser == null) {

                user = new User();
                user.setUsername(username);
                user.setEmail(email);
                doInsert = true;
            } else {
                return ResultFactory.getFailResult("Unable to add new user: the email address is already in use");
            }
        } else {
            if(testByEmailUser == null){
                user.setEmail(email);
            } else if(! user.equals(testByEmailUser)){
                return ResultFactory.getFailResult("The email address is already in use by username="
                        + testByEmailUser.getUsername()
                        + "and cannot be assigned to " + username);
            }
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setAdminRole(adminRole);

        if(doInsert) {
            userDao.persist(user);
        } else {
            user = userDao.merge(user);
        }

        return ResultFactory.getSuccessResult(user);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result<User> remove(String username, String actionUsername){

        User actionUser = userDao.find(actionUsername);
        
        if (!actionUser.isAdmin()) {
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if (actionUsername.equalsIgnoreCase(username)) {
            return ResultFactory.getFailResult("It is not allowed to delete yourself!");
        }

        if (username == null) {
            return ResultFactory.getFailResult("Unable to remove null User");
        } 

        User user = userDao.findByUsername(username);
        long taskLogCount = taskLogDao.findTaskLogCountByUser(user);

        if (user == null) {
            return ResultFactory.getFailResult("Unable to load User for removal with username=" + username);
        } else if(taskLogCount > 0) {
            return ResultFactory.getFailResult("Unable to remove User with username=" + username + " as valid task logs are assigned");
        } else {

            userDao.remove(user);
            String msg = "User " + username + " was deleted by " + actionUsername;
            logger.info(msg);

            return ResultFactory.getSuccessResultMsg(msg);
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<User>> findAll(String actionUsername){
        if (isValidUser(actionUsername)) {
            return ResultFactory.getSuccessResult(userDao.findAll());
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<User> findByUsernamePassword(String username, String password){

        User user = userDao.findByUsernamePassword(username, password);
        if (user == null) {
            return ResultFactory.getFailResult("Unable to verify user/password combination!");
        } else {
            return ResultFactory.getSuccessResult(user);
        }
    }
}
