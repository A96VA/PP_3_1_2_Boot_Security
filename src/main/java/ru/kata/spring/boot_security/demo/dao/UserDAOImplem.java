package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class UserDAOImplem implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByName(String username) {
        return entityManager.createQuery("select n from User n join fetch n.roles where n.username = :id", User.class)
                .setParameter("id", username)
                .getResultList().stream().findAny().orElse(null);
    }

    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public Set<User> listUsers() {
        return new LinkedHashSet<>(entityManager.createQuery("select l from User l", User.class).getResultList());
    }

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }
}