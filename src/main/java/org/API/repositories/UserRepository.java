// import jakarta.enterprise.context.ApplicationScoped; // @ApplicationScoped
// import jakarta.persistence.EntityManager; // EntityManager
// import jakarta.persistence.PersistenceContext; // @PersistenceContext

// @ApplicationScoped
// public class UserRepository {

// @PersistenceContext
// EntityManager em;

// public void save(User user) {
// em.persist(user);
// }

// public boolean existsByApiKey(String apiKey) {
// Long Count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.apiKey =
// :key", Long.class

// )
// .setParameter("key", apiKey)
// .getSingleResult();
// return count > 0;
// }

// public User findByApiKey(String apiKey) {
// return em.createQuery(
// String query = "SELECT u FROM User u WHERE u.apiKey = :key", User.class)
// .setParameter("key", apiKey)
// .getSingleResult();
// }
// }

package org.API.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.API.entities.UserEntity;

@ApplicationScoped
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(UserEntity user) {
        em.persist(user);
    }

    //Jag tror inte att den här metoden kommer behövas men vi kan låta den vara så länge.
    public boolean existsByApiKey(String apiKey) {
        Long count = em.createQuery(
                "SELECT COUNT(u) FROM UserEntity u WHERE u.apiKey = :key",
                Long.class)
                .setParameter("key", apiKey)
                .getSingleResult();

        return count > 0;
    }

    public UserEntity saveOrUpdate(UserEntity user) {
    if (user.getId() == null) {
        em.persist(user);
        return user;
    } else {
        return em.merge(user);
    }
}

    public UserEntity findByApiKey(String apiKey) {
        return em.createQuery(
                "SELECT u FROM UserEntity u WHERE u.apiKey = :key",
                UserEntity.class)
                .setParameter("key", apiKey)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
