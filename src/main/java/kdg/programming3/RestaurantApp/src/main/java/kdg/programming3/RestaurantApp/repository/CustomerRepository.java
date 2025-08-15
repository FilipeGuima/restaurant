package kdg.programming3.RestaurantApp.repository;

import kdg.programming3.RestaurantApp.domain.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    void deleteById(Long id);
    Customer save(Customer customer);
    List<Customer> search(String name, String email, LocalDate creationDate, Long id);
    List<Customer> findByEmail(String email);
}
