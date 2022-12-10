package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    boolean addRole(Role role);
    Role findByNameRole(String name);
    List<Role> listRoles();
    Role findByIdRole(int id);
    Set<Role> listByRole(List<String> name);
    boolean save(User user);
    List<User> listUsers();
    void delete(int id);
    void update(User user);
    User findById(int id);
    User findByUsername(String userName);
}