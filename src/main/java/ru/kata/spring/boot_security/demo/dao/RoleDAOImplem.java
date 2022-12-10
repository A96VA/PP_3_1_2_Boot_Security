package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImplem implements RoleDAO {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Role user) {
        entityManager.persist(user);
    }

    @Override
    public Role findByIdRole(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> listRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role findByName(String name) {
        return entityManager.createQuery("select n from Role n where n.role = :id", Role.class)
                .setParameter("id", name)
                .getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Role> listByName(List<String> name) {
        return  entityManager.createQuery("select n from Role n where n.role in (:id)", Role.class)
                .setParameter("id", name)
                .getResultList();
    }
}
