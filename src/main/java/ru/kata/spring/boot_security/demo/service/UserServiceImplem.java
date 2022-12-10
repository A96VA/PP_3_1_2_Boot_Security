package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImplem implements UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    public UserServiceImplem(RoleDAO roleDAO, UserDAO userDAO) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }

    @Override
    public boolean addRole(Role role) {
        Role userPrimary = roleDAO.findByName(role.getRole());
        if(userPrimary != null) {return false;}
        roleDAO.save(role);
        return true;
    }

    @Override
    public Role findByNameRole(String name) {
        return roleDAO.findByName(name);
    }

    @Override
    public List<Role> listRoles() {
        return roleDAO.listRoles();
    }

    @Override
    public Role findByIdRole(int id) {
        return roleDAO.findByIdRole(id);
    }

    @Override
    public Set<Role> listByRole(List<String> name) {
        Set<Role> roles = new HashSet<>(roleDAO.listByName(name));
        return roles;
    }

    @Override
    public boolean save(User user) {
        User userPrimary = userDAO.findByName(user.getUsername());
        if(userPrimary != null) {return false;}
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        userDAO.save(user);
        return true;
    }

    @Override
    public List<User> listUsers() {
        return userDAO.listUsers();
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Override
    public void update(User user) {
        User userPrimary = findById(user.getId());
         if(!userPrimary.getPassword().equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        }
        userDAO.update(user);
    }
    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }
    @Override
    public User findByUsername(String userName) {
        return userDAO.findByName(userName);
    }
}