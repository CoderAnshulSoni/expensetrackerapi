package com.anshul.expensetrackerapi.service;


import com.anshul.expensetrackerapi.Entity.User;
import com.anshul.expensetrackerapi.Entity.UserModel;
import com.anshul.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.anshul.expensetrackerapi.exception.ResourceNotFoundException;
import com.anshul.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("User with email: " + email + " not found"));
    }

    @Override
    public User createUser(UserModel model) {

        if(userRepo.existsByEmail(model.getEmail())){
            throw new ItemAlreadyExistsException("User with email " + model.getEmail() + " already registered");
        }

        User user = new User();
        BeanUtils.copyProperties(model, user);
        user.setPassword(bCryptPasswordEncoder.encode(model.getPassword()));
        return userRepo.save(user);

    }

    @Override
    public User readUser() {
        Long id = getLoggedInUser().getId();
        return userRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User with id: " + id + " not found"));
    }

    @Override
    public User updateUser(UserModel model) {
        User ouser = readUser();
        ouser.setName(model.getName() != null ? model.getName() : ouser.getName());
        ouser.setEmail(model.getEmail() != null ? model.getEmail() : ouser.getEmail());
        ouser.setPassword(model.getPassword() != null ? bCryptPasswordEncoder.encode(model.getPassword()) : ouser.getPassword());
        ouser.setAge(model.getAge() != null ? model.getAge() : ouser.getAge());

        return userRepo.save(ouser);
    }

    @Override
    public void deleteUser() {
        User user = readUser();
        userRepo.delete(user);
    }
}
