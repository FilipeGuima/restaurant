package kdg.programming3.RestaurantApp.repository.Jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile({"jpa-h2", "jpa-postgres"})
@Transactional
public class MenuItemRepositoryJpa implements MenuItemRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MenuItem> findAll() {
        return em.createQuery("SELECT m FROM MenuItem m", MenuItem.class)
                .getResultList();
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return Optional.ofNullable(em.find(MenuItem.class, id));
    }

    @Override
    public MenuItem save(MenuItem item) {
        if (item.getId() == null) {
            em.persist(item);
            return item;
        }
        return em.merge(item);
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }
}
