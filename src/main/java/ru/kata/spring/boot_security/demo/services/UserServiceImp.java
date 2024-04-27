package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        if (userRepository.findById(id).isEmpty())
            throw new UsernameNotFoundException("Пользователь с таким ID не найден");
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public boolean deleteUserById(Long id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty())
            throw new UsernameNotFoundException("Пользователя с таким юзернеймом");
        return userRepository.findByUsername(username).get();
    }

    @Override
    @Transactional
    public void updateUser(User userUpd, Long id) {
        User userDb = userRepository.findById(id).get();
//        userDb.setPassword(userUpd.getPassword());
        userDb.setUsername(userUpd.getUsername());
        userDb.setRoles((List<Role>) userUpd.getAuthorities());

        if (userDb.getPassword().equals(userUpd.getPassword())) {
            userRepository.save(userDb);
        } else {
            userDb.setPassword(passwordEncoder.encode(userUpd.getPassword()));
            userRepository.save(userDb);
        }
        System.out.println(userDb.toString());
        userRepository.save(userDb);
    }
}
