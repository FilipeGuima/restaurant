package kdg.programming3.RestaurantApp.repository.Memory;

import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import kdg.programming3.RestaurantApp.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("inmemory")
public class InMemoryCustomerRepository implements CustomerRepository {

    @Override
    public List<Customer> findAll() {
        return InMemoryDataFactory.getCustomers();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return InMemoryDataFactory.getCustomers().stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        InMemoryDataFactory.getOrders().removeIf(order -> order.getCustomer().getId().equals(id));

        InMemoryDataFactory.getCustomers().removeIf(customer -> customer.getId().equals(id));
    }

    @Override
    public Customer save(Customer customer) {
        long nextId = InMemoryDataFactory.getCustomers().stream()
                .mapToLong(Customer::getId)
                .max()
                .orElse(0L) + 1;

        customer.setId(nextId);
        customer.setCreationDate(LocalDate.now());
        InMemoryDataFactory.getCustomers().add(customer);
        return customer;
    }

    @Override
    public List<Customer> search(String name, String email, LocalDate creationDate, Long id) {
        return InMemoryDataFactory.getCustomers().stream()
                .filter(customer -> name == null || name.isEmpty()
                        || customer.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                           customer.getLastName().toLowerCase().contains(name.toLowerCase())
                        )
                .filter(customer -> email == null || email.isEmpty() || customer.getEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(customer -> creationDate == null || customer.getCreationDate().isEqual(creationDate))
                .filter((customer -> id == null || id.equals(customer.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return InMemoryDataFactory.getCustomers().stream()
                .filter(customer -> email != null
                && email.equalsIgnoreCase(customer.getEmail()))
                .collect(Collectors.toList());
    }
}
