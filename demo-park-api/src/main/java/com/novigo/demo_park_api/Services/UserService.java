package com.novigo.demo_park_api.Services;

import java.util.List;

import com.novigo.demo_park_api.Exception.PasswordInvalidException;
import lombok.RequiredArgsConstructor;
import com.novigo.demo_park_api.Entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.novigo.demo_park_api.Repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.novigo.demo_park_api.Exception.EntityNotFoundException;
import com.novigo.demo_park_api.Exception.UsernameUniqueViolationException;


@Component
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User save(User user) {

        try {
            return userRepository.save(user);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username %s is already created", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User id %s not found", id)));
    }

    @Transactional
    public User updatePassword(Long id, String actualPass, String newPass, String confirmPass) {
        if (!newPass.equals(confirmPass)){
            throw new PasswordInvalidException("New password doesn't match with password confirmation.");
        }

        User user = findById(id);
        if (!user.getPassword().equals(actualPass)){
            throw new PasswordInvalidException("Your password is incorrect");
        }

        if(actualPass.equals(newPass)){
            throw new PasswordInvalidException("Current and new passwords cannot be the same.");
        }

        user.setPassword(newPass);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
