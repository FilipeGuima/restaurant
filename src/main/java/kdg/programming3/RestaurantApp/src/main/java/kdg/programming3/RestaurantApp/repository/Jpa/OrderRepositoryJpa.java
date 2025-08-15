package kdg.programming3.RestaurantApp.repository.Jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;
import kdg.programming3.RestaurantApp.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile({"jpa-h2", "jpa-postgres"})
@Transactional
public class OrderRepositoryJpa implements OrderRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class)
                .getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            em.persist(order);
            return order;
        }
        return em.merge(order);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return em.createQuery("SELECT o FROM Order o WHERE o.status = :status", Order.class)
                .setParameter("status", status)
                .getResultList();
    }
}
