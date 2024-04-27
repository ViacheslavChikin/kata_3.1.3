package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserService {
    public List<User> findAll();
    public User findUserById(Long id);
    public boolean deleteUserById(Long id);
    public void saveUser(User user);
    public User findByUsername(String username);
    public void updateUser(User user, Long id);
}
