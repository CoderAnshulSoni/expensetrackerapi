package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.User;
import com.anshul.expensetrackerapi.Entity.UserModel;
import org.springframework.stereotype.Service;

public interface UserService {

    User getLoggedInUser();

    User createUser(UserModel model);

    User readUser();

    User updateUser(UserModel model);

    void deleteUser();

}
