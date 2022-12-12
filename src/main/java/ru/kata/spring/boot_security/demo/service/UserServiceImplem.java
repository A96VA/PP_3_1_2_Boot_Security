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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            throw new UsernameNotFoundException(String.format("User '%s' error", username));
        }
        return user;
    }



    @Override
    public LinkedHashSet<Role> listRoles() {
        return new LinkedHashSet<>(roleDAO.listRoles());
    }

    @Override
    public Set<Role> listByRole(List<String> name) {
        Set<Role> rolesSet = new HashSet<>(roleDAO.listByName(name));
        return rolesSet;
    }

    @Override
    public boolean save(User user) {
        User userPrimary = userDAO.findByName(user.getUsername());
        if(userPrimary != null) {return false;}
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        List<String> list = user.getRoles().stream().map(i -> i.getRole()).collect(Collectors.toList());
        Set<Role> set = listByRole(list);
        user.setRoles(set);
        userDAO.save(user);
        return true;
    }

    @Override
    public Set<User> listUsers() {
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
        List<String> list = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
        Set<Role> set = listByRole(list);
        user.setRoles(set);
        userDAO.update(user);
    }
    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }



}