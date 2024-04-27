package ru.kata.spring.boot_security.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    //SpringSec ищет данный метод и передает на сверку юзернэйм, если он есть и все ок,
    // то мы возвращаем ему UserDetails
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDb = userRepository.findByUsername(username);
        if (userDb.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Пользователя с ником '%s', нет в базе", username));
        }
        return userDb.get();
    }
}
