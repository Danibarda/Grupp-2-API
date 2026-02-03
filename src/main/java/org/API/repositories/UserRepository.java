import jakarta.enterprise.context.ApplicationScoped;  // @ApplicationScoped
import jakarta.persistence.EntityManager;             // EntityManager
import jakarta.persistence.PersistenceContext;        // @PersistenceContext

@ApplicationScoped
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public boolean existsByApiKey(String apiKey) {
        String query = "SELECT COUNT(u) FROM User u WHERE u.apiKey = :key";
        Long count = em.createQuery(query, Long.class)
                       .setParameter("key", apiKey)
                       .getSingleResult();
        return count > 0;
    }

    public User findByApiKey(String apiKey) {
        return em.createQuery(
        String query = "SELECT u FROM User u WHERE u.apiKey = :key", User.class)
                 .setParameter("key", apiKey)
                 .getSingleResult();
}
}
