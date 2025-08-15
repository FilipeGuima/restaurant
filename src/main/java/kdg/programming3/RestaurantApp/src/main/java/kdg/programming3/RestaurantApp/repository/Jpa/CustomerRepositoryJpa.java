package kdg.programming3.RestaurantApp.repository.Jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile({"jpa-h2", "jpa-postgres"})
@Transactional
public class CustomerRepositoryJpa implements CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
            return customer;
        }
        return em.merge(customer);
    }

    @Override
    public List<Customer> search(String name, String email, LocalDate creationDate, Long id) {
        String jpql = "SELECT c FROM Customer c WHERE " +
                "(:name IS NULL OR c.firstName LIKE CONCAT('%', :name, '%') " +
                " OR c.lastName LIKE CONCAT('%', :name, '%')) AND " +
                "(:email IS NULL OR c.email LIKE CONCAT('%', :email, '%')) AND " +
                "(:creationDate IS NULL OR c.creationDate = :creationDate) AND " +
                "(:id IS NULL OR c.id = :id)";

        return em.createQuery(jpql, Customer.class)
                .setParameter("name", name)
                .setParameter("email", email)
                .setParameter("creationDate", creationDate)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", email)
                .getResultList();
    }
}
