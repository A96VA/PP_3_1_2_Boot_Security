package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;
import java.util.List;
public interface RoleDAO {
    void save(Role user);
    Role findByIdRole(int id);
    List<Role> listRoles();
    Role findByName(String name);
    List<Role> listByName(List<String> name);
}